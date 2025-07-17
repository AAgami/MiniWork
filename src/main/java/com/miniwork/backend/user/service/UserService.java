package com.miniwork.backend.user.service;

import com.miniwork.backend.user.dto.LoginRequest;
import com.miniwork.backend.user.dto.LoginResponse;
import com.miniwork.backend.user.dto.SignupRequest;
import com.miniwork.backend.user.dto.UserResponse;

/* 사용자 관련 서비스 인터페이스 */
/* 회원가입, 로그인, 내 정보 조회 등의 기능 정의 */
public interface UserService {
    // 회원가입 처리
    // @param request 클라이언트에서 전달한 회원가입 요청 정보
    // @return 가입된 사용자 정보 응답 DTO(UserResponse)

    UserResponse signup(SignupRequest signupRequest);
    LoginResponse login(LoginRequest loginRequest); // 추가
}
