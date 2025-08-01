package com.miniwork.backend.organization.mapper;

import com.miniwork.backend.organization.dto.MembershipResponse;
import com.miniwork.backend.organization.entity.Membership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MembershipMapper {
    @Mapping(target="userId", source="user.id")
    @Mapping(target="userEmail",source="user.email")
    @Mapping(target="invitedAt", source="invitedAt")
    @Mapping(target="expiresAt", source="expiresAt")
    MembershipResponse toResponse(Membership membership);
}
