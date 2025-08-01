package com.miniwork.backend.organization.service;

import com.miniwork.backend.common.exception.CustomException;
import com.miniwork.backend.common.exception.ErrorCode;
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

import java.time.LocalDateTime;
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

    // 1) 새로운 조직을 생성 후 생성자를 admin 권한으로 자동 등록
    @Override
    public OrganizationResponse createOrganization(CreateOrganizationRequest request, String creatorEmail) {
        // 1-1) 도메인 중복 체크
        if (organizationRepository.existsByDomain(request.getDomain())) {
            throw new CustomException(ErrorCode.DOMAIN_DUPLICATED);
        }

        // 1-2) DTO -> Organization 엔티티로 변환 후 저장
        Organization org = organizationMapper.toEntity(request);
        org = organizationRepository.save(org);

        // 1-3) 생성자 User 조회
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 1-4) ADMIN 권한으로 Membership 생성 및 저장 (APPROVED)
        Membership adminMembership = Membership.builder()
                .organization(org)
                .user(creator)
                .role(Role.ADMIN)
                .status(MembershipStatus.APPROVED)  // 생성 즉시 승인 상태
                .build();
        membershipRepository.save(adminMembership);

        // 1-5) 생성된 조직 정보를 DTO로 변환하여 반환
        return organizationMapper.toResponse(org);
    }

    // 2) 조직에 새로운 멤버 초대 (INVITED)
    @Override
    public MembershipResponse inviteMember(Long orgId, InviteMemberRequest request) {
        // 2-1) 조직 조회 없으면 INVALID_INPUT
        Organization org = organizationRepository.findById(orgId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));

        // 2-2) 초대할 사용자 조회 (없으면 USER_NOT_FOUND)
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 2-3) 이미 조직 멤버인지 확인 (이미 멤버면 INVALID_INPUT)
        if (membershipRepository.findByOrganizationAndUser(org, user).isPresent()){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        LocalDateTime now = LocalDateTime.now();

        // 2-4) INVITED 상태로 Membership 엔티티 생성 및 저장
        Membership invitation = Membership.builder()
                .organization(org)
                .user(user)
                .status(MembershipStatus.INVITED)
                .role(Role.MEMBER)
                .invitedAt(now)
                .expiresAt(now.plusDays(3))
                .build();
        membershipRepository.save(invitation);

        return membershipMapper.toResponse(invitation);
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
