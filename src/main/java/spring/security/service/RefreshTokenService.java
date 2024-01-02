package spring.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.security.domain.RefreshToken;
import spring.security.repository.RefreshTokenRepository;
import spring.security.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.refresh-expiration}")
    private long jwtRefreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

//    public RefreshTokenService(@Value("jwt.refresh-expiration") long jwtRefreshExpiration) {
//        this.jwtRefreshExpiration = jwtRefreshExpiration;
//    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        // TODO error handling
        refreshToken.setUser(userRepository.findById(userId).get());

        refreshToken.setExpiration(Instant.now().plusMillis(jwtRefreshExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

}
