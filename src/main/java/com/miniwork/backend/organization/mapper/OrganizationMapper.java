package com.miniwork.backend.organization.mapper;

import com.miniwork.backend.organization.dto.CreateOrganizationRequest;
import com.miniwork.backend.organization.dto.OrganizationResponse;
import com.miniwork.backend.organization.entity.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper {
    Organization toEntity(CreateOrganizationRequest dto);
    OrganizationResponse toResponse(Organization organization);
}