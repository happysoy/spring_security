package spring.security.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.config.jwt.JwtUtils;
import spring.security.domain.ERole;
import spring.security.domain.User;
import spring.security.dto.request.SignUpRequest;
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

    @Override
    @Transactional
    public SignUpRequest singUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(ExceptionStatus.DUPLICATE_EMAIL);
        }

        User entity = request.toEntity();
        entity.setUserRole(ERole.ROLE_USER);
        entity.setPassword(hashPassword(request.password()));

        userRepository.save(entity);

        return request;
    }

    @Override
    public String signIn(User user) {
        // 인증 성공 시 사용자 정보(UserDetails) 로드
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return jwtUtils.generateJwtToken(authenticate);
    }

    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
