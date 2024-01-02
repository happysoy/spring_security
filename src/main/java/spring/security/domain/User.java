package spring.security.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "\"user\"",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NoArgsConstructor
@Getter
@DynamicInsert //column default value 넣기
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private ERole role;

    @ColumnDefault("'ACTIVE'")
    private String status;

    // TODO refreshToken Redis 로 관리
    private String refreshToken;

    public User(String username, String email, String password, ERole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
