package spring.security.services;

import spring.security.domain.User;
import spring.security.dto.request.SignUpRequest;

public interface UserService {
    SignUpRequest singUp(SignUpRequest signUpRequest);

    String signIn(User user);

    String hashPassword(String password);
}
