package spring.security.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.security.domain.ERole;
import spring.security.domain.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Test
    void findByUserName() throws Exception{
        //given
        User user = new User("신짱구", "a@gmail.com", "a", ERole.ROLE_USER);
        userRepository.save(user);

        //when
        User findUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        //then
        Assertions.assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
    }
}