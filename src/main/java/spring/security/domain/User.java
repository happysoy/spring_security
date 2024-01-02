package spring.security.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "\"user\"",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DynamicInsert //column default value 넣기
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

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
    @OneToOne(mappedBy = "user", fetch= FetchType.LAZY)
    private RefreshToken refreshToken;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setUserRole(ERole role) {
        this.role = role;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
