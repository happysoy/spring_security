package spring.security.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private RedisService redisService;

    @Test
    void getRedis() {
        //given
        String key = "token";
        String value = "email";

        long expiredTime = 30 * 1000; // 30초
        redisService.setRedisTemplate(key, value, Duration.ofMillis(expiredTime));

        //when
        String returnValue = redisService.getRedisTemplateValue(key);

        //then
        assertThat(value).isEqualTo(returnValue);
        assertThat(value).isEqualTo("email");
    }
}