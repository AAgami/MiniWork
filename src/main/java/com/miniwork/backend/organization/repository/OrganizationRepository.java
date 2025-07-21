package com.miniwork.backend.organization.repository;

import com.miniwork.backend.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    //도메인 중복 체크용
    boolean existsByDomain(String domain);
}
