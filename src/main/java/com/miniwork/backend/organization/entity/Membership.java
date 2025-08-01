package com.miniwork.backend.organization.entity;

import com.miniwork.backend.common.exception.CustomException;
import com.miniwork.backend.common.exception.ErrorCode;
import com.miniwork.backend.user.entity.User;
import com.miniwork.backend.user.entity.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "membership")
@Getter@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //조직과의 연관관계
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    //ADMIN, MEMBER, GUEST
    //UserRole과 동일해서 돌려쓰고싶은데 추후 확장성을 위해 분리..
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus status;

    @Column(nullable = false)
    private LocalDateTime invitedAt;

    @Column(nullable = true)
    private LocalDateTime expiresAt;

    @PrePersist
    private void prePersist() {
        this.invitedAt = LocalDateTime.now();
    }

    // 초대 상태 → 승인 상태로 전환
    public void approve() {
        if (this.status != MembershipStatus.INVITED) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR);
        }
        this.status = MembershipStatus.APPROVED;
    }
    // 역할 변경이 필요할 때
    public void changeRole(Role newRole) {
        this.role = newRole;
    }

}
