package com.miniwork.backend.organization.service;

import com.miniwork.backend.organization.dto.CreateOrganizationRequest;
import com.miniwork.backend.organization.dto.InviteMemberRequest;
import com.miniwork.backend.organization.dto.MembershipResponse;
import com.miniwork.backend.organization.dto.OrganizationResponse;
import com.miniwork.backend.organization.entity.Role;

import java.util.List;

public interface OrganizationService {
    /** 조직 생성 및 생성자 ADMIN 자동 부여 */
    OrganizationResponse createOrganization(CreateOrganizationRequest dto, String creatorEmail);

    /** 조직 내 멤버 초대 */
    MembershipResponse inviteMember(Long orgId, InviteMemberRequest dto);

    /** 가입 승인 (INVITED → APPROVED) */
    MembershipResponse approveMember(Long orgId, Long membershipId);

    /** 멤버 역할 변경 */
    MembershipResponse changeMemberRole(Long orgId, Long membershipId, Role newRole);

    /** 조직의 전체 멤버 조회 */
    List<MembershipResponse> listMembers(Long orgId);
}
