package com.miniwork.backend.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateOrganizationRequest {
    private String domain;
    private String name;
}
