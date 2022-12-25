package com.winterdevcamp.authentication.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class ViewUserResDto {
    private String loginId;
    private String password;
    private String nickName;
    private String email;
    private String authority;
}
