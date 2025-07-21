package com.miniwork.backend.organization.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="organization")
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA용
@AllArgsConstructor //전체 필드 생성자
@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 조직 도메인 (e.g: miniwork.dev)
    @Column(nullable = false, unique = true)
    private String domain;

    // 조직 이름 (e.g. miniwork)
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // 도메인·이름 변경이 필요할 때만 이 메서드 사용
    public void updateInfo(String domain, String name) {
        this.domain = domain;
        this.name = name;
    }
}
