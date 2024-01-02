package spring.security.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import spring.security.domain.RefreshToken;
import spring.security.domain.User;
import spring.security.dto.request.SignInRequest;
import spring.security.repository.RefreshTokenRepository;
import spring.security.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RefreshTokenServiceTest {

    @Value("${jwt.refresh-expiration}")
    private long jwtRefreshExpiration;

    @Autowired
    UserServiceImpl userService;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    UserRepository userRepository;

    @Test
    void refresh_존재_확인() {
        assertThat(jwtRefreshExpiration).isNotNull();
    }

    
    @Test
    void 이미_존재하는_토큰_삭제() throws Exception{
        //given
        SignInRequest signInRequest = new SignInRequest("q", "q");
        userService.signIn(signInRequest);
        //when
        User savedUser = userRepository.findByEmail(signInRequest.email()).orElse(null);
//        RefreshToken findToken = refreshTokenRepository.findById(user.getId()).orElse(null);

        refreshTokenService.deleteByUserId(savedUser.getId());

        User emptyUser = userRepository.findByEmail(signInRequest.email()).orElse(null);
        //then
        assertThat(emptyUser).isNull();
    }
}