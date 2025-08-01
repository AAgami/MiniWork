package com.miniwork.backend.user.service;

import com.miniwork.backend.auth.JwtTokenProvider;
import com.miniwork.backend.common.exception.CustomException;
import com.miniwork.backend.common.exception.ErrorCode;
import com.miniwork.backend.user.dto.LoginRequest;
import com.miniwork.backend.user.dto.LoginResponse;
import com.miniwork.backend.user.dto.SignupRequest;
import com.miniwork.backend.user.dto.UserResponse;
import com.miniwork.backend.user.entity.User;
import com.miniwork.backend.user.entity.UserRole;
import com.miniwork.backend.user.entity.UserStatus;
import com.miniwork.backend.user.mapper.UserMapper;
import com.miniwork.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserResponse getCurrentUser() {
        // 1. SecurityContext에서 인증 정보(Principal(email)) 꺼내기
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //2. DB에서 User 엔티티 조회
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //3. DTO 변환 후 반환
        return userMapper.toResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 1. 이메일로 유저 조회
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 2. 비밀번호 일치 여부 학인
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 3. 마지막 로그인 시간 갱신
        user.updateLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        //4. 토큰
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());

        // 5. 응답 객체 반환
        return new LoginResponse(user.getName(), user.getEmail(), token);
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
        User user = userMapper.toEntity(signupRequest);  // DTO -> Entity
        user.setPassword(encodedPassword);      // 비밀번호 암호화
        user.setRole(UserRole.MEMBER);             // 기본 역할 설정
        user.setStatus(UserStatus.OFFLINE);        // 기본 상태 설정

        // 4. 저장
        userRepository.save(user);

        // 5. 응답용 DTO로 변환하여 return
        return userMapper.toResponse(user);
    }
}
