package com.winterdevcamp.authentication.authentication;

import com.winterdevcamp.authentication.dto.AuthenticationReqDto;
import com.winterdevcamp.authentication.dto.TokenDto;
import com.winterdevcamp.authentication.user.UserEntity;
import com.winterdevcamp.authentication.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;

    private static final String SECRET_KEY = "secrettxvcvsadvsdvsdvsdvsdgvbsdfbdfbfdbdfvsdvsdvsdvsddvsdvsdsdvdsb";
    private final String AccessTokenSub = "AccessToken";
    private final String RefreshTokenSub = "RefreshToken";
    private final long AccessTokenExpTime = 30*1000*60;
    private final long RefreshTokenExpTime = 7*24*60*1000*60;

    public String createAccessToken(UserEntity userEntity) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(AccessTokenSub)
                .claim("loginId", userEntity.getLoginId())
                .claim("id",userEntity.getUserId() )
                .claim("nickName", userEntity.getNickName())
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date((System.currentTimeMillis()+AccessTokenExpTime)))
                .compact();
    }

    public String createRefreshToken() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(RefreshTokenSub)
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date((System.currentTimeMillis()+RefreshTokenExpTime)))
                .compact();
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public TokenDto authenticate(AuthenticationReqDto authenticationReqDto) throws Exception {
        UserEntity userEntity = userRepository
                .findByLoginIdAndPassword(authenticationReqDto.getLoginId(), authenticationReqDto.getPassword())
                .orElseThrow(()->new Exception("해당되는 회원이 없습니다"));

        String accessToken = createAccessToken(userEntity);
        String refreshToken = createRefreshToken();

        AuthenticationEntity authentication = AuthenticationEntity.builder()
                .user(userEntity)
                .refreshToken(refreshToken)
                .build();

        authenticationRepository.save(authentication);

        TokenDto tokenDto = TokenDto.builder()
                .AccessToken(accessToken)
                .RefreshToken(refreshToken)
                .build();

        return tokenDto;
    }
}
