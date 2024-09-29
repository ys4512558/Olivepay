package kr.co.olivepay.member.service.impl;

import kr.co.olivepay.core.member.dto.res.MemberRoleRes;
import kr.co.olivepay.core.member.dto.res.DuplicateRes;
import kr.co.olivepay.member.dto.req.MemberRegisterReq;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.enums.Role;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.handler.AppException;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.mapper.MemberMapper;
import kr.co.olivepay.member.repository.MemberRepository;
import kr.co.olivepay.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public SuccessResponse<MemberRoleRes> getMemberRole(Long memberId){
        Optional<Member> member = memberRepository.findById(memberId);
        String role = member.map(m -> m.getRole().name()).orElse("");

        MemberRoleRes response = new MemberRoleRes(role);
        return new SuccessResponse<>(GET_MEMBER_ROLE_SUCCESS, response);
    }

    @Override
    public SuccessResponse<NoneResponse> promoteUser(Long memberId){
        // 회원 조회
        Member member = validateMemberId(memberId);

        // 일반 유저로 전환 및 저장
        member.promoteUser();
        memberRepository.save(member);

        return new SuccessResponse<>(USER_PROMOTE_SUCCESS, NoneResponse.NONE);
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

    /**
     * MemberID로 회원을 찾는 메소드<br>
     * 없다면 NOT_FOUND_MEMBER Error
     * @param memberId
     * @return
     */
    private Member validateMemberId(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(NOT_FOUND_MEMBER));
    }
}
