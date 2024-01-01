package spring.security.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import spring.security.services.UserDetailsImpl;

import java.security.Key;
import java.util.Date;


@Slf4j
@Component
public class JwtUtils {

    @Value("app.jwt-secret")
    private String jwtSecret;

    private static final long jwtExpiration = 24 * 60 * 60 * 1000; // 1일


//    @Value("app.jwt-expiration")
//    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken(String token) {
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
