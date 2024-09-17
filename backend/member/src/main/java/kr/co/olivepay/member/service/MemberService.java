package kr.co.olivepay.member.service;


import kr.co.olivepay.member.dto.DuplicateRes;
import kr.co.olivepay.member.global.response.SuccessResponse;

public interface MemberService {

    SuccessResponse<DuplicateRes> checkPhoneNumberDuplicate(String phoneNumber);
}
