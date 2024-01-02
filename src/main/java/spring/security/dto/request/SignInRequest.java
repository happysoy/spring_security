package spring.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import spring.security.domain.User;

public record SignInRequest(
        @NotBlank
        String email,
        @NotBlank
        String password
) {

        public User toEntity() {
                return User.builder()
                        .email(email)
                        .password(password)
                        .build();
        }
}
