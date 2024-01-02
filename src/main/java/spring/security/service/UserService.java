package spring.security.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import spring.security.dto.request.SignInRequest;
import spring.security.dto.request.SignUpRequest;
import spring.security.dto.response.UserInfoResponse;

public interface UserService {
    SignUpRequest singUp(SignUpRequest signUpRequest);

    ResponseEntity<?> signIn(SignInRequest signInRequest);

    ResponseEntity<?> refreshToken(HttpServletRequest request);
    String hashPassword(String password);
}
