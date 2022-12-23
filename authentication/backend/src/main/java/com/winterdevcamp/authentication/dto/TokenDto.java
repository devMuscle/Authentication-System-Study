package com.winterdevcamp.authentication.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class TokenDto {
    String AccessToken;
    String RefreshToken;
}
