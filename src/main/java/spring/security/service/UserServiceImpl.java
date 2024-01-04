package spring.security.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.config.jwt.JwtUtils;
import spring.security.config.security.UserDetailsImpl;
import spring.security.domain.ERole;
import spring.security.domain.User;
import spring.security.dto.request.SignInRequest;
import spring.security.dto.request.SignUpRequest;
import spring.security.dto.response.UserInfoResponse;
import spring.security.exception.CustomException;
import spring.security.exception.ExceptionStatus;
import spring.security.exception.ExceptionStatusProvider;
import spring.security.exception.response.MessageResponse;
import spring.security.repository.UserRepository;

import java.time.Duration;
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
    public SignUpRequest singUp(SignUpRequest request) {
        // 비밀번호 != 비밀번호 확인
        if (!Objects.equals(request.password(), request.passwordCheck())) {
            throw new CustomException(ExceptionStatus.FAIL_PASSWORD_CHECK);
        }

        // 이메일 중복 확인
        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(ExceptionStatus.DUPLICATE_EMAIL);
        }

        User entity = request.toEntity(hashPassword(request.password()));
        entity.changeUserRole(ERole.ROLE_USER);

        userRepository.save(entity);

        return request;
    }

    @Override
    @Transactional
    public ResponseEntity<?> signIn(SignInRequest request) {
        // 이메일 존재 확인
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(ExceptionStatus.FAIL_LOGIN));

        // 유효한 이메일, 비밀번호인지 확인
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new CustomException(ExceptionStatus.FAIL_LOGIN);
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
                .body(new UserInfoResponse(user.getId(), user.getUsername(), user.getEmail()));
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
                    .orElseThrow(() -> new CustomException(ExceptionStatus.EXPIRED_TOKEN));
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
