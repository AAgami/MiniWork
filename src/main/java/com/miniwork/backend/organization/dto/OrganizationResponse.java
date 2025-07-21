package com.miniwork.backend.organization.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrganizationResponse {
    private Long id;
    private String domain;
    private String name;
    private LocalDateTime createdAt;
}
