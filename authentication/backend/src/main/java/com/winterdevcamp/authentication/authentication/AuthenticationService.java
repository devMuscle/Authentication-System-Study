package com.winterdevcamp.authentication.authentication;

import com.winterdevcamp.authentication.dto.AuthenticationReqDto;
import com.winterdevcamp.authentication.dto.TokenDto;
import com.winterdevcamp.authentication.token.JwtTokenProvider;
import com.winterdevcamp.authentication.token.TokenEntity;
import com.winterdevcamp.authentication.token.TokenRepository;
import com.winterdevcamp.authentication.user.UserEntity;
import com.winterdevcamp.authentication.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenDto authenticate(AuthenticationReqDto authenticationReqDto) throws Exception {
        UserEntity userEntity = userRepository
                .findByLoginIdAndPassword(authenticationReqDto.getLoginId(), authenticationReqDto.getPassword())
                .orElseThrow(()->new Exception("해당되는 회원이 없습니다"));

        String accessToken = jwtTokenProvider.createAccessToken(userEntity);
        String refreshToken = jwtTokenProvider.createRefreshToken();

        TokenEntity authentication = TokenEntity.builder()
                .user(userEntity)
                .refreshToken(refreshToken)
                .build();

        tokenRepository.save(authentication);

        TokenDto tokenDto = TokenDto.builder()
                .AccessToken(accessToken)
                .RefreshToken(refreshToken)
                .build();

        return tokenDto;
    }
}
