package com.winterdevcamp.authentication.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class SignUpReqDto {
    private String loginId;
    private String password;
    private String nickName;
    private String email;

    public void encryptPw(String encryptedPw) {
        this.password=encryptedPw;
    }
}
