package com.winterdevcamp.authentication.authentication;

import com.winterdevcamp.authentication.dto.AuthenticationReqDto;
import com.winterdevcamp.authentication.dto.TokenDto;
import com.winterdevcamp.authentication.token.JwtTokenProvider;
import com.winterdevcamp.authentication.token.TokenEntity;
import com.winterdevcamp.authentication.token.TokenRepository;
import com.winterdevcamp.authentication.user.UserEntity;
import com.winterdevcamp.authentication.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
        String refreshToken = jwtTokenProvider.createRefreshToken(userEntity);

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

    public void validateRefreshToken(String refreshToken, TokenEntity tokenEntity) throws Exception {
        if(!refreshToken.equals(tokenEntity.getRefreshToken())) {
            throw new RuntimeException("토큰의 값이 일치하지 않습니다.");
        }
    }

    public TokenDto reissue(String refreshToken) throws Exception {
        TokenEntity tokenEntity = tokenRepository.findByUserId(jwtTokenProvider.getUserId(refreshToken))
                .orElseThrow(()->new RuntimeException("로그아웃한 회원입니다"));

        validateRefreshToken(refreshToken, tokenEntity);

        String accessToken = jwtTokenProvider.createAccessToken(tokenEntity.getUser());

        TokenDto tokenDto = TokenDto.builder()
                .AccessToken(accessToken)
                .build();

        return tokenDto;
    }
}
