package com.miniwork.backend.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*개발 중에만 인증 없이 모든 요청을 허용하는 시큐리티 설정*/
@Configuration
public class SecurityConfig {
    /*TODO: 로그인 기능 구현과 JWT 적용시 개발 예정*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //csrf 비활성화
        http
                .csrf(csrf -> csrf.disable())
                //모든 요청 허용
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                //기본 로그인 폼 제거
                .formLogin(login -> login.disable())
                //기본 인증창 제거
                .httpBasic(httpBasic -> httpBasic.disable());
        return http.build();
    }

}
