package com.miniwork.backend.organization.service;

import com.miniwork.backend.organization.dto.CreateOrganizationRequest;
import com.miniwork.backend.organization.dto.InviteMemberRequest;
import com.miniwork.backend.organization.dto.MembershipResponse;
import com.miniwork.backend.organization.dto.OrganizationResponse;
import com.miniwork.backend.organization.entity.Membership;
import com.miniwork.backend.organization.entity.MembershipStatus;
import com.miniwork.backend.organization.entity.Organization;
import com.miniwork.backend.organization.entity.Role;
import com.miniwork.backend.organization.mapper.MembershipMapper;
import com.miniwork.backend.organization.mapper.OrganizationMapper;
import com.miniwork.backend.organization.repository.MembershipRepository;
import com.miniwork.backend.organization.repository.OrganizationRepository;
import com.miniwork.backend.user.entity.User;
import com.miniwork.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/* 조직 생성, 멤버 초대/승인/역할 변경, 멤버 조회 기능 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final OrganizationMapper organizationMapper;
    private final MembershipMapper membershipMapper;

    // 새로운 조직을 생성 후 생성자를 admin 권한으로 자동 등록
    @Override
    public OrganizationResponse createOrganization(CreateOrganizationRequest request, String creatorEmail) {
        // 1) 도메인 중복 체크
        if (organizationRepository.existsByDomain(request.getDomain())) {
            throw new IllegalArgumentException("이미 존재하는 도메인입니다.");
        }

        // 2) Organization 엔티티로 변환 후 저장
        Organization org = organizationMapper.toEntity(request);
        org = organizationRepository.save(org);

        // 3) 생성자 조회
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 4) ADMIN 권한으로 Membership 생성 및 저장
        Membership adminMembership = Membership.builder()
                .organization(org)
                .user(creator)
                .role(Role.ADMIN)
                .status(MembershipStatus.APPROVED)  // 생성 즉시 승인 상태
                .build();
        membershipRepository.save(adminMembership);

        // 5) 생성된 조직 정보를 DTO로 변환하여 반환
        return organizationMapper.toResponse(org);
    }

    @Override
    public MembershipResponse inviteMember(Long orgId, InviteMemberRequest dto) {
        return null;
    }

    @Override
    public MembershipResponse approveMember(Long orgId, Long membershipId) {
        return null;
    }

    @Override
    public MembershipResponse changeMemberRole(Long orgId, Long membershipId, Role newRole) {
        return null;
    }

    @Override
    public List<MembershipResponse> listMembers(Long orgId) {
        return List.of();
    }
}
