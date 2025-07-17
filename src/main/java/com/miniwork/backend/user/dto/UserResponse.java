package com.miniwork.backend.user.dto;

import com.miniwork.backend.user.entity.User;
import com.miniwork.backend.user.entity.UserRole;
import com.miniwork.backend.user.entity.UserStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 클라이언트에 응답할 때 사용하는 사용자 정보 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    // 사용자 고유 식별자
    private Long id;

    // 이메일 주소 (로그인 계정)
    private String email;

    // 사용자 이름 또는 닉네임
    private String name;

    // 역할 (ADMIN, MEMBER, GUEST 등)
    private UserRole role;

    // 현재 상태 (온라인, 휴가, 회의 등)
    private UserStatus status;

    // 마지막 로그인 시각
    private LocalDateTime lastLoginAt;

    // 계정 생성 시각
    private LocalDateTime createdAt;
}