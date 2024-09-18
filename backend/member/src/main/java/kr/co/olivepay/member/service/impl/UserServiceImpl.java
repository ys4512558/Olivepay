package kr.co.olivepay.member.service.impl;

import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.entity.User;
import kr.co.olivepay.member.enums.Role;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.enums.SuccessCode;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.mapper.UserMapper;
import kr.co.olivepay.member.openapi.dto.res.member.MemberCreateAndSearchRes;
import kr.co.olivepay.member.openapi.service.FintechService;
import kr.co.olivepay.member.repository.UserRepository;
import kr.co.olivepay.member.service.MemberService;
import kr.co.olivepay.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.olivepay.member.global.enums.ErrorCode.FINTECH_API_ID_ALREADY_EXISTS;
import static kr.co.olivepay.member.global.enums.SuccessCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final String EMAIL_SUFFIX = "@ssafy.co.kr";

    private final MemberService memberService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FintechService fintechService;

    @Override
    @Transactional
    public SuccessResponse<NoneResponse> registerUser(UserRegisterReq request) {
        // Member 생성
        Member savedMember = memberService.registerMember(request.memberRegisterReq(), Role.TEMP_USER);

        // 금융망 API 유저 등록
        MemberCreateAndSearchRes memberRes;
        try {
            memberRes = fintechService.createMember(savedMember.getPhoneNumber() + EMAIL_SUFFIX);
        } catch (Exception e) {
            // E4002: 이미 존재하는 ID일 때
            if (FINTECH_API_ID_ALREADY_EXISTS.getMessage().equals(e.getMessage())) {
                // 이미 존재하는 ID라면 searchMember 호출
                memberRes = fintechService.searchMember(savedMember.getPhoneNumber() + EMAIL_SUFFIX);
            } else {
                // 그 외 예외는 다시 던짐
                throw e;
            }
        }

        // User 생성
        User user = userMapper.toUser(request, savedMember, memberRes.getUserKey());

        // 저장
        userRepository.save(user);
        return new SuccessResponse<>(USER_CREATED, NoneResponse.NONE);
    }
}
