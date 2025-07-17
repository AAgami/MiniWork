package com.miniwork.backend.user.controller;

import com.miniwork.backend.user.dto.LoginRequest;
import com.miniwork.backend.user.dto.LoginResponse;
import com.miniwork.backend.user.dto.SignupRequest;
import com.miniwork.backend.user.dto.UserResponse;
import com.miniwork.backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* 사용자 관련 API 요청 처리 컨트롤러 */
@RestController
@RequestMapping("/api/users") // 모든 경로는 /api/users 하위로 시작
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /* 회원가입 요청 처리 */
    /* POST /api/users/signup */
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody @Valid SignupRequest signupRequest) {
        UserResponse response = userService.signup(signupRequest);
        return ResponseEntity.ok(response);
    }

    /* 로그인 요청 처리 */
    /* POST /api/users/login */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
