package spring.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import spring.security.domain.ERole;

import java.util.Set;


public record SignUpRequest(
        @NotBlank
        String username,

        @NotBlank
        String email,

        @NotBlank
        String password,

        ERole role
) {
}
