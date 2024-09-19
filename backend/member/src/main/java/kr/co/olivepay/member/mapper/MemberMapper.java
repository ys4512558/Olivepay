package kr.co.olivepay.member.mapper;

import kr.co.olivepay.member.dto.req.MemberRegisterReq;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(source = "memberRegisterReq.name", target = "name")
    @Mapping(source = "memberRegisterReq.phoneNumber", target = "phoneNumber")
    @Mapping(source = "hashedPassword", target = "password")
    @Mapping(source = "role", target = "role")
    Member toMember(MemberRegisterReq memberRegisterReq, String hashedPassword, Role role);

}
