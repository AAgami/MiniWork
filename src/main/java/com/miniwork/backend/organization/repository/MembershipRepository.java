package com.miniwork.backend.organization.repository;

import com.miniwork.backend.organization.entity.Membership;
import com.miniwork.backend.organization.entity.Organization;
import com.miniwork.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    // 특정 조직의 모든 멤버 조회
    List<Membership> findByOrganization(Organization organization);

    // 조직과 사용자 기준 단일 멤버 조회
    Optional<Membership> findByOrganizationAndUser(Organization organization, User user);

}
