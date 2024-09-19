package kr.co.olivepay.member.mapper;

import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "savedMember", target = "member")
    @Mapping(source = "userRegisterReq.nickname", target = "nickname")
    @Mapping(source = "userRegisterReq.birthdate", target = "birthdate")
    @Mapping(source = "userRegisterReq.pin", target = "pin")
    @Mapping(source = "userKey", target = "userKey")
    User toUser(UserRegisterReq userRegisterReq, Member savedMember, String userKey);

}
