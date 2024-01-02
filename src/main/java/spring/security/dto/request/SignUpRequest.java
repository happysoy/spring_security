package spring.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import spring.security.domain.ERole;
import spring.security.domain.User;

import java.util.Set;


public record SignUpRequest(
        @NotBlank
        String username,

        @NotBlank
        String email,

        @NotBlank
        String password

) {
        public User toEntity() {
                return User.builder()
                        .username(username)
                        .email(email)
                        .password(password)
                        .build();
        }
}
