package kr.co.olivepay.member.service.impl;

import kr.co.olivepay.member.dto.DuplicateRes;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.repository.MemberRepository;
import kr.co.olivepay.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.co.olivepay.member.global.enums.SuccessCode.*;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public SuccessResponse<DuplicateRes> checkPhoneNumberDuplicate(String phoneNumber) {
        Boolean isDuplicated = memberRepository.existsByPhoneNumber(phoneNumber);
        DuplicateRes response = new DuplicateRes(isDuplicated);
        return new SuccessResponse<>(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS, response);
    }
}
