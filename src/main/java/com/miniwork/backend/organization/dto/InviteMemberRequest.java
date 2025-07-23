package com.miniwork.backend.organization.dto;

import lombok.*;

@Getter
@NoArgsConstructor @AllArgsConstructor @Builder
public class InviteMemberRequest {
    private String email;
}
