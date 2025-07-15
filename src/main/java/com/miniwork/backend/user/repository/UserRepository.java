package com.miniwork.backend.user.repository;

import com.miniwork.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/* User Entity에 대한 DBV 접근을 담당하는 JPA Repository */
/* Spring Data JPA가 자동으로 구현체를 생성한다 */
public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일 사용자 조회
    Optional<User> findByEmail(String email);

    // 이메일 중복체크
    // 회원 가입시 중복 이메일 방지용
    boolean existsByEmail(String email);

}
