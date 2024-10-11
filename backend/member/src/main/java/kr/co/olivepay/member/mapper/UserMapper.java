package kr.co.olivepay.member.mapper;

import kr.co.olivepay.core.member.dto.res.UserNicknameRes;
import kr.co.olivepay.core.member.dto.res.UserNicknamesRes;
import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.dto.res.UserInfoRes;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "savedMember", target = "member")
    @Mapping(source = "userRegisterReq.nickname", target = "nickname")
    @Mapping(source = "userRegisterReq.birthdate", target = "birthdate")
    @Mapping(source = "userRegisterReq.pin", target = "pin")
    @Mapping(source = "userKey", target = "userKey")
    User toUser(UserRegisterReq userRegisterReq, Member savedMember, String userKey);


    @Mapping(source = "user.member.id", target = "memberId")
    @Mapping(source = "user.member.name", target = "name")
    @Mapping(source = "user.member.phoneNumber", target = "phoneNumber")
    @Mapping(source = "user.nickname", target = "nickname")
    @Mapping(source = "user.member.role", target = "role")
    UserInfoRes toUserInfoRes(User user);

    @Mapping(source = "user.member.id", target = "memberId")
    @Mapping(source = "user.nickname", target = "nickname")
    UserNicknameRes toUserNicknameRes(User user);

    List<UserNicknameRes> toUserNicknameRes(List<User> user);

    /**
     * 유저의 닉네임 목록 반환 시 사용
     * @param users
     * @return
     */
    default UserNicknamesRes toUserNicknamesRes(List<User> users){
        return UserNicknamesRes.builder()
                               .members(toUserNicknameRes(users))
                               .build();
    }
}
