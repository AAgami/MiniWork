package com.miniwork.backend.organization.dto;

import com.miniwork.backend.organization.entity.MembershipStatus;
import com.miniwork.backend.organization.entity.Role;
import lombok.*;

import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MembershipResponse {
    private Long id;
    private Long userId;
    private String userEmail;
    private Role role;
    private MembershipStatus status;
    private LocalDateTime joinedAt;
}
