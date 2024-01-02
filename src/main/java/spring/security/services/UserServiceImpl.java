package spring.security.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import spring.security.domain.User;
import spring.security.dto.request.SignUpRequest;
import spring.security.dto.response.MessageResponse;
import spring.security.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long singUp(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException();
        }
        user.setUserRole(ERole.ROLE_USER);
        user.setPassword(hashPassword(user.getPassword()));

        return userRepository.save(user).getUserId();
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
