package com.miniwork.backend.user.service;

import com.miniwork.backend.common.exception.CustomException;
import com.miniwork.backend.common.exception.ErrorCode;
import com.miniwork.backend.user.dto.LoginRequest;
import com.miniwork.backend.user.dto.SignupRequest;
import com.miniwork.backend.user.dto.UserResponse;
import com.miniwork.backend.user.entity.User;
import com.miniwork.backend.user.entity.UserRole;
import com.miniwork.backend.user.entity.UserStatus;
import com.miniwork.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        // 1. 이메일로 유저 조회
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUNT));

        // 2. 비밀번호 일치 여부 학인
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 3. 마지막 로그인 시간 갱신
        user.updateLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        // 4. 응답 객체 반환
        return UserResponse.from(user);
    }

    @Override
    public UserResponse signup(SignupRequest signupRequest) {
        // 1. 이메일 중복 검사
        if (userRepository.existsByEmail(signupRequest.getEmail())){
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }
        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        // 3. User 객체 생성
        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .name(signupRequest.getName())
                .role(UserRole.MEMBER)
                .status(UserStatus.OFFLINE)
                .build();
        // 4. 저장
        userRepository.save(user);

        // 5. 응답용 DTO로 변환하여 return
        return UserResponse.from(user);
    }
}
