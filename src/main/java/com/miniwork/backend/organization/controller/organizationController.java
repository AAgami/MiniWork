package com.miniwork.backend.organization.controller;

import com.miniwork.backend.organization.dto.CreateOrganizationRequest;
import com.miniwork.backend.organization.dto.OrganizationResponse;
import com.miniwork.backend.organization.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/* 조직 관련 API 요청 처리 컨트롤러 */
@RestController
@RequestMapping("/api/orgs")
@RequiredArgsConstructor
public class organizationController {
    private final OrganizationService organizationService;

    /* 조직 생성 (생성 요청 DTO, 현재 인증된 사용자 정보(email))*/
    /* POST /api/orgs/ */
    @PostMapping
    public OrganizationResponse createOrganization(@Valid @RequestBody CreateOrganizationRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        String creatorEmail = userDetails.getUsername();
        return organizationService.createOrganization(request, creatorEmail);
    }
}
