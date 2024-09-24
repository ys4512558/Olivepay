package kr.co.olivepay.member.service;


import kr.co.olivepay.core.member.dto.res.MemberRoleRes;
import kr.co.olivepay.core.member.dto.res.DuplicateRes;
import kr.co.olivepay.member.dto.req.MemberRegisterReq;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.enums.Role;
import kr.co.olivepay.member.global.response.SuccessResponse;

public interface MemberService {

    Member registerMember(MemberRegisterReq request, Role role);

    SuccessResponse<DuplicateRes> checkPhoneNumberDuplicate(String phoneNumber);

    SuccessResponse<MemberRoleRes> getMemberRole(Long memberId);
}
