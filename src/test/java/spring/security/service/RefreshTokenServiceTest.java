package spring.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RefreshTokenServiceTest {

    @Value("${jwt.refresh-expiration}")
    private long jwtRefreshExpiration;


    @Test
    void refresh_존재_확인() {
        assertThat(jwtRefreshExpiration).isNotNull();
    }

}