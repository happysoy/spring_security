package spring.security.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import spring.security.services.UserDetailsImpl;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 파싱, 생성, 검증 제공
 */
@Slf4j
@Component
public class JwtUtils {


    @Value("${jwt.secret}")
    String secretKeyPlain;
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public JwtUtils(@Value("${jwt.secret}") String secretKeyPlain,
                    @Value("${jwt.expiration}") long jwtExpiration) {
        this.secretKeyPlain = secretKeyPlain;
        this.jwtExpiration = jwtExpiration;
    }



    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userDetails.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyPlain));
    }
    /**
     plain -> 시크릿 키 객체 변환
     */
//    private SecretKey key() {
//        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
//        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
//    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token={}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Jwt token is expired={}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Jwt token is unsupported={}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Jwt claims string is empty={}", e.getMessage());
        }
        return false;
    }


}
