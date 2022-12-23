package com.winterdevcamp.authentication.authentication;

import com.winterdevcamp.authentication.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity(name="Authentication")
public class AuthenticationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authenticationId;

    @Column(length = 500)
    private String refreshToken;

    @OneToOne
    @JoinColumn(name="userId")
    private UserEntity user;
}
