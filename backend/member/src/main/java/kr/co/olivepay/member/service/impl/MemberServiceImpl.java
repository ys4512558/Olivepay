package kr.co.olivepay.member.service.impl;

import kr.co.olivepay.member.dto.res.DuplicateRes;
import kr.co.olivepay.member.dto.req.MemberRegisterReq;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.enums.Role;
import kr.co.olivepay.member.global.handler.AppException;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.mapper.MemberMapper;
import kr.co.olivepay.member.repository.MemberRepository;
import kr.co.olivepay.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kr.co.olivepay.member.global.enums.ErrorCode.*;
import static kr.co.olivepay.member.global.enums.SuccessCode.*;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member registerMember(MemberRegisterReq request, Role role){
        // 전화번호 중복 확인
        validateByPhoneNumber(request.phoneNumber());

        // Member Entity
        Member member =
                memberMapper.toMember(request, passwordEncoder.encode(request.password()), role);

        return memberRepository.save(member);
    }

    @Override
    public SuccessResponse<DuplicateRes> checkPhoneNumberDuplicate(final String phoneNumber) {
        Boolean isDuplicated = memberRepository.existsByPhoneNumber(phoneNumber);
        DuplicateRes response = new DuplicateRes(isDuplicated);
        return new SuccessResponse<>(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS, response);
    }

    /**
     * 전화번호 중복 검사 메소드<br>
     * 중복 시 PHONE_NUMBER_DUPLICATED Error
     * @param phoneNumber 11자리 전화번호
     */
    private void validateByPhoneNumber(final String phoneNumber){
        if(memberRepository.existsByPhoneNumber(phoneNumber)){
            throw new AppException(PHONE_NUMBER_DUPLICATED);
        }
    }
}
