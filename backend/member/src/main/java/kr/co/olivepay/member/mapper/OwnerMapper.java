package kr.co.olivepay.member.mapper;

import kr.co.olivepay.member.dto.req.OwnerRegisterReq;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OwnerMapper {

    @Mapping(source = "savedMember", target = "member")
    @Mapping(source = "ownerRegisterReq.rrnPrefix", target = "rrnPrefix")
    @Mapping(source = "ownerRegisterReq.rrnCheckDigit", target = "rrnCheckDigit")
    Owner toOwner(OwnerRegisterReq ownerRegisterReq, Member savedMember);
}
