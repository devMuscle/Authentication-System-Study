package com.winterdevcamp.authentication.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class AuthenticationReqDto {
    private String loginId;
    private String password;
}
