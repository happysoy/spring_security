package spring.security.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import spring.security.common.exception.response.DataMessageResponse;
import spring.security.domain.User;
import spring.security.dto.request.ChangePasswordRequest;
import spring.security.dto.request.SignInRequest;
import spring.security.dto.request.SignUpRequest;
import spring.security.dto.response.UserAndTokenResponse;
import spring.security.dto.response.UserInfoResponse;

public interface UserService {
    ResponseEntity<UserInfoResponse> signUp(SignUpRequest signUpRequest);

    ResponseEntity<?> signIn(SignInRequest signInRequest);

    ResponseEntity<?> signOut(HttpServletRequest request);

    ResponseEntity<?> refreshToken(HttpServletRequest request);
    String hashPassword(String password);

    void changePassword(User user, ChangePasswordRequest request);

    void uploadProfile(User user, String upload);

    void deleteProfile(User user);


}
