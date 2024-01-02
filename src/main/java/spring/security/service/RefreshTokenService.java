package spring.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.security.domain.RefreshToken;
import spring.security.exception.CustomException;
import spring.security.exception.ExceptionStatus;
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

        // TODO error handling 이미 존재하는 refreshtoken 삭제

        refreshToken.setUser(userRepository.findById(userId).get());

        refreshToken.setExpiration(Instant.now().plusMillis(jwtRefreshExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken =  refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiration().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new CustomException(ExceptionStatus.EXPIRED_REFRESH_TOKEN);
        }
        return refreshToken;
    }
}
