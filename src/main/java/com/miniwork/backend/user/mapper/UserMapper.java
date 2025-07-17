package com.miniwork.backend.user.mapper;

import com.miniwork.backend.user.dto.SignupRequest;
import com.miniwork.backend.user.dto.UserResponse;
import com.miniwork.backend.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * User ↔ DTO 변환을 담당하는 Mapper
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface UserMapper {
    // User 엔티티를 응답용 DTO로 변환
    UserResponse toResponse(User user);

    // 회원가입 요청을 User 엔티티로 변환
    User toEntity(SignupRequest signupRequest);
}
