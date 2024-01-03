package spring.security.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import spring.security.domain.User;

public record SignInRequest(
        @NotBlank @Email
        String email,
        @NotBlank @Length(min=8, max=20)
        String password
) {
        public User toEntity() {
                return User.builder()
                        .email(email)
                        .password(password)
                        .build();
        }
}
