package spring.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.security.config.jwt.JwtUtils;
import spring.security.dto.request.SignUpRequest;
import spring.security.dto.response.MessageResponse;
import spring.security.domain.User;
import spring.security.repository.UserRepository;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@Validated @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("이미 사용중인 이메일입니다"));
        }

        User user = new User(signUpRequest.username(), signUpRequest.email(), signUpRequest.password(), signUpRequest.role());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("회원가입 성공"));
    }


}
