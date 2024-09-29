package kr.co.olivepay.member.service.impl;

import kr.co.olivepay.member.dto.req.UserPasswordChangeReq;
import kr.co.olivepay.member.dto.req.UserPasswordCheckReq;
import kr.co.olivepay.member.dto.res.UserPasswordCheckRes;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.repository.MemberRepository;
import kr.co.olivepay.member.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kr.co.olivepay.member.global.enums.SuccessCode.PASSWORD_CHANGE_SUCCESS;
import static kr.co.olivepay.member.global.enums.SuccessCode.PASSWORD_CHECK_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SuccessResponse<UserPasswordCheckRes> checkUserPassword(
            Long memberId, UserPasswordCheckReq request)
    {
        Member member = memberRepository.getById(memberId);
        boolean isCorrect = passwordEncoder.matches(request.password(), member.getPassword());

        UserPasswordCheckRes response = new UserPasswordCheckRes(isCorrect);
        return new SuccessResponse<>(PASSWORD_CHECK_SUCCESS, response);
    }

    @Override
    public SuccessResponse<NoneResponse> changeUSerPassword(
            Long memberId, UserPasswordChangeReq request)
    {
        Member member = memberRepository.getById(memberId);
        member.updatePassword(passwordEncoder.encode(request.password()));

        memberRepository.save(member);
        return new SuccessResponse<>(PASSWORD_CHANGE_SUCCESS, NoneResponse.NONE);
    }
}
