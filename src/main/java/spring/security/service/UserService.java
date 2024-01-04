package spring.security.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import spring.security.config.security.UserDetailsImpl;
import spring.security.domain.User;
import spring.security.dto.request.SignInRequest;
import spring.security.dto.request.SignUpRequest;
import spring.security.dto.response.UserInfoResponse;

public interface UserService {
    SignUpRequest singUp(SignUpRequest signUpRequest);

    ResponseEntity<?> signIn(SignInRequest signInRequest);

    ResponseEntity<?> signOut(HttpServletRequest request);

    ResponseEntity<?> refreshToken(HttpServletRequest request);
    String hashPassword(String password);

    void uploadProfile(User user, String upload);

    void deleteProfile(User user);

}
