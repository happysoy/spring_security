package spring.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ChangePasswordRequest(
        @NotBlank @Length(min=8, max=20)
        String password,

        @NotBlank
        String passwordCheck
) { }
