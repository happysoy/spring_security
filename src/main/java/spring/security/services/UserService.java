package spring.security.services;

import spring.security.domain.User;
import spring.security.dto.request.SignUpRequest;

public interface UserService {
    Long singUp(User user);

    String signIn(User user);

    String hashPassword(String password);
}
