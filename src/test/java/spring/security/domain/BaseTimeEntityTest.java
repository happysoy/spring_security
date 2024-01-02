package spring.security.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.security.repository.UserRepository;
import spring.security.services.UserDetailsServiceImpl;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BaseTimeEntityTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void BaseTimeEntity_등록() throws Exception{
        //given
        LocalDateTime now = LocalDateTime.of(2024, 1, 2, 0, 0, 0);
        User user = new User("신짱구", "a@gmail.com", "a");

        //when
        userRepository.save(user);
        User findUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        //then
        assertThat(findUser.getCreatedAt()).isAfter(now);
    }
}