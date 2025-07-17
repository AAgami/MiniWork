package com.miniwork.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private String name;  // 사용자 이름
    private String email;
    private String token;
}
