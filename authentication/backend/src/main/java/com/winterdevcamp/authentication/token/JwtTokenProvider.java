package com.winterdevcamp.authentication.token;

import com.winterdevcamp.authentication.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "secrettxvcvsadvsdvsdvsdvsdgvbsdfbdfbfdbdfvsdvsdvsdvsddvsdvsdsdvdsb";
    private final long ACCESS_Token_EXP_TIME = 30*60*1000L;
    private final long REFRESH_TOKEN_EXP_TIME = 7*24*60*60*1000L;

    public String createAccessToken(UserEntity userEntity) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(userEntity.getLoginId())
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("userId",userEntity.getUserId() )
                .claim("nickName", userEntity.getNickName())
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date((System.currentTimeMillis()+ACCESS_Token_EXP_TIME)))
                .compact();
    }

    public String createRefreshToken(UserEntity userEntity) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(userEntity.getLoginId())
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date((System.currentTimeMillis()+REFRESH_TOKEN_EXP_TIME)))
                .compact();
    }

    public String getSubject(String token) throws Exception{
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getUserId(String token) throws NumberFormatException{
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long userId = Long.valueOf(String.valueOf(claims.get("userId")));

        return userId;
    }
}
