package com.winterdevcamp.authentication.user;

import com.winterdevcamp.authentication.enums.Authority;
import com.winterdevcamp.authentication.token.TokenEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity(name="User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length=15)
    private String loginId;

    @Column(length=15)
    private String password;

    @Column(length=15)
    private String nickName;

    @Column(length=20)
    private String email;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Authority authority = Authority.USER;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private TokenEntity token;

    public void updateAuthority() {
        if(this.authority.equals(Authority.ADMIN)) {
            this.authority=Authority.USER;
        }else {
            this.authority=Authority.ADMIN;
        }
    }
}
