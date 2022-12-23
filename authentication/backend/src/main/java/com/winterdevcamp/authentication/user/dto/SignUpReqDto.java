package com.winterdevcamp.authentication.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class SignUpReqDto {
    String loginId;
    String password;
    String nickName;
    String email;
}
