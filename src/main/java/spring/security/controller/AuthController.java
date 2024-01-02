package spring.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.security.config.jwt.JwtUtils;
import spring.security.dto.request.SignInRequest;
import spring.security.dto.request.SignUpRequest;
import spring.security.dto.response.DataMessageResponse;
import spring.security.dto.response.UserInfoResponse;
import spring.security.exception.ExceptionStatusProvider;
import spring.security.repository.UserRepository;
import spring.security.service.MessageResponseService;
import spring.security.service.UserService;


@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final MessageResponseService message;

    @PostMapping("/signup")
    public DataMessageResponse<SignUpRequest> signUpUser(@Validated @RequestBody SignUpRequest request, Errors errors) {
        if (errors.hasErrors()) {
            ExceptionStatusProvider.throwError(errors);
        }

        return message.getDataResponse(userService.singUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@Validated @RequestBody SignInRequest request, Errors errors) {
        if (errors.hasErrors()) {
            ExceptionStatusProvider.throwError(errors);
        }
        return userService.signIn(request);
    }

}
