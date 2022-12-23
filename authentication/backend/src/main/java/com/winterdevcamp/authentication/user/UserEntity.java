package com.winterdevcamp.authentication.user;

import com.winterdevcamp.authentication.authentication.AuthenticationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToOne(mappedBy = "user")
    private AuthenticationEntity token;
}
