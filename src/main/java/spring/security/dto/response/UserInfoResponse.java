package spring.security.dto.response;

public record UserInfoResponse(
        Long id,
        String username,
        String email

) {
}
