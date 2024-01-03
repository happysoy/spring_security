package spring.security.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import spring.security.domain.ERole;
import spring.security.domain.User;

import java.util.Set;


public record SignUpRequest(
        @NotBlank
        String username,

        @NotBlank @Email
        String email,

        @NotBlank @Length(min=8, max=20)
        String password,

        @NotBlank
        String passwordCheck

) {
        public User toEntity(String hashPassword) {
                return User.builder()
                        .username(username)
                        .email(email)
                        .password(hashPassword)
                        .build();
        }
}
