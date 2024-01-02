package spring.security;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spring.security.domain.ERole;
import spring.security.domain.User;
import spring.security.dto.request.SignUpRequest;
import spring.security.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.dbInitAdmin();
        initService.dbInitUsers();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

//        private final EntityManager em;
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

//        public void dbInitAdmin() {
//            extracted("a", "a", "관리자", Role.ADMIN);
//        }

        public void dbInitUsers() {
            extracted("신짱구", "q", "q");
            extracted("신짱아", "z", "z");
        }

        private void extracted(String username, String email, String password) {
            String hashPassword = passwordEncoder.encode(password);
            User user = new User(username, email, hashPassword);
            user.setUserRole(ERole.ROLE_USER);
            userRepository.save(user);

        }



    }
}