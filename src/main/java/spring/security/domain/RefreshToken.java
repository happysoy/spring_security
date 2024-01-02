package spring.security.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
//@Table(name="\"refreshToken\"")
@Getter @Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="refresh_token_id")
    private long id;


    @OneToOne(mappedBy = "refreshToken", fetch = FetchType.LAZY)
    private User user;

    @Column
    public String token;

    @Column
    private Instant expiration;
}
