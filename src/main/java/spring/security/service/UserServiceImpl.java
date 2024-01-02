package spring.security.service;

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
import spring.security.domain.ERole;
import spring.security.domain.RefreshToken;
import spring.security.domain.User;
import spring.security.dto.request.SignInRequest;
import spring.security.dto.request.SignUpRequest;
import spring.security.dto.response.UserInfoResponse;
import spring.security.exception.CustomException;
import spring.security.exception.ExceptionStatus;
import spring.security.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Transactional
    public SignUpRequest singUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(ExceptionStatus.DUPLICATE_EMAIL);
        }

        User entity = request.toEntity(hashPassword(request.password()));
        entity.setUserRole(ERole.ROLE_USER);

        userRepository.save(entity);

        return request;
    }

    @Override
    public ResponseEntity<?> signIn(SignInRequest request) {
        // TODO 로그인 실패 예외처리

        // 인증 성공 시 사용자 정보(UserDetails) 로드
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();

        ResponseCookie jwtAccessCookie = jwtUtils.generateAccessJwtCookie(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
    }

    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
