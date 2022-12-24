package com.winterdevcamp.authentication.token;

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
@Entity(name="Token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column(length = 500)
    private String refreshToken;

    @OneToOne
    @JoinColumn(name="userId")
    private UserEntity user;
}
