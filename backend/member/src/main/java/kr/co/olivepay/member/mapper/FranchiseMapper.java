package kr.co.olivepay.member.mapper;

import kr.co.olivepay.core.franchise.dto.req.FranchiseCreateReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

    default String toRegistrationNumber(FranchiseCreateReq franchiseCreateReq) {
        return franchiseCreateReq != null ? franchiseCreateReq.registrationNumber() : null;
    }
}
