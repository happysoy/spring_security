package spring.security.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.common.exception.GlobalCustomException;
import spring.security.common.exception.MessageResponse;
import spring.security.common.exception.request.ClientInvalidPassword;
import spring.security.common.exception.request.ExpiredToken;
import spring.security.common.exception.response.*;
import spring.security.config.jwt.JwtUtils;
import spring.security.config.security.UserDetailsImpl;
import spring.security.domain.ERole;
import spring.security.domain.User;
import spring.security.dto.request.ChangePasswordRequest;
import spring.security.dto.request.SignInRequest;
import spring.security.dto.request.SignUpRequest;
import spring.security.dto.response.UserInfoResponse;
import spring.security.repository.UserRepository;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;



    @Override
    @Transactional
    public ResponseEntity<UserInfoResponse> signUp(SignUpRequest request) {
        // 비밀번호 != 비밀번호 확인
        if (!Objects.equals(request.password(), request.passwordCheck())) {
            throw IncorrectPasswordCheck.EXCEPTION;
        }

        // 이메일 중복 확인
        if (userRepository.existsByEmail(request.email())) {
            throw DuplicateEmail.EXCEPTION;
        }

        User entity = request.toEntity(hashPassword(request.password()));
        entity.changeUserRole(ERole.ROLE_USER);

        userRepository.save(entity);

        return ResponseEntity.ok()
                .body(UserInfoResponse.builder().
                        username(entity.getUsername()).
                        email(entity.getEmail()).
                        build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> signIn(SignInRequest request) {
        // 이메일 존재 확인
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> UserNotFound.EXCEPTION);

        // 유효한 이메일, 비밀번호인지 확인
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw FailLogin.EXCEPTION;
        }

//        Authentication authenticate = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.email(), request.password())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authenticate);


        ResponseCookie jwtAccessCookie = jwtUtils.generateAccessJwtCookie(user);

        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());
        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken);


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new UserInfoResponse(user.getUsername(), user.getEmail()));
    }

    @Override
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getRefreshJwtFromCookies(request);

        if ((refreshToken != null) && (!refreshToken.isEmpty())) {
            String email = redisService.getRedisTemplateValue(refreshToken);
            return userRepository.findByEmail(email)
                    .map(user -> {
                        ResponseCookie jwtAccessCookie = jwtUtils.generateAccessJwtCookie(new UserDetailsImpl(user).getUser());
                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                                .body(new MessageResponse("access token 재발급 성공"));
                    })
                    .orElseThrow(() -> ExpiredToken.EXCEPTION);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("refresh token이 http cookie에 없습니다"));
    }

    @Override
    public ResponseEntity<?> signOut(HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String refreshToken = jwtUtils.getRefreshJwtFromCookies(request);
        if (!principal.toString().equals("anonymousUser")) {
            redisService.deleteRedisTemplateValue(refreshToken);
        }

        ResponseCookie cleanJwtAccessCookie = jwtUtils.getCleanAccessJwtCookie();
        ResponseCookie cleanJwtRefreshCookie = jwtUtils.getCleanRefreshJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanJwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, cleanJwtRefreshCookie.toString())
                .body(new MessageResponse("로그아웃 성공"));

    }

    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }


    @Override
    @Transactional
    public void changePassword(User user, ChangePasswordRequest request) {
        // 비밀번호 != 비밀번호 확인
        if (!Objects.equals(request.password(), request.passwordCheck())) {
            throw IncorrectPasswordCheck.EXCEPTION;
        }

        user.changePassword(hashPassword(request.password()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteProfile(User user) {
        user.changeProfileImg(null);
        userRepository.save(user);
    }

    // /profile-img?image=abc
    @Override
    @Transactional
    public void uploadProfile(User user, String upload) {
        user.changeProfileImg(upload);
        userRepository.save(user);
    }


}