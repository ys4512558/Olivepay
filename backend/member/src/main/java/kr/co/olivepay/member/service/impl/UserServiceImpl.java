package kr.co.olivepay.member.service.impl;

import kr.co.olivepay.core.member.dto.res.UserKeyRes;
import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.entity.User;
import kr.co.olivepay.member.enums.Role;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.enums.SuccessCode;
import kr.co.olivepay.member.global.handler.AppException;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.mapper.UserMapper;
import kr.co.olivepay.member.openapi.dto.res.member.MemberCreateAndSearchRes;
import kr.co.olivepay.member.openapi.service.FintechService;
import kr.co.olivepay.member.repository.MemberRepository;
import kr.co.olivepay.member.repository.UserRepository;
import kr.co.olivepay.member.service.MemberService;
import kr.co.olivepay.member.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static kr.co.olivepay.member.global.enums.ErrorCode.FINTECH_API_ID_ALREADY_EXISTS;
import static kr.co.olivepay.member.global.enums.ErrorCode.NOT_FOUND_MEMBER;
import static kr.co.olivepay.member.global.enums.SuccessCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final String EMAIL_SUFFIX = "@ssafy.co.kr";

    private final MemberService memberService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FintechService fintechService;
    private final MemberRepository memberRepository;

    /**
     * 유저 정보를 받아 회원 가입하는 메소드
     * @param request
     * @return
     */
    @Override
    public SuccessResponse<NoneResponse> registerUser(UserRegisterReq request) {
        // 금융망 API 유저 등록
        String userKey = registerFintechMember(request.memberRegisterReq().phoneNumber());

        // 유저 등록
        registerUser(request, userKey);
        
        // 반환
        return new SuccessResponse<>(USER_CREATED, NoneResponse.NONE);
    }

    /**
     * 멤버ID를 받아 금융망 API UserKey를 반환하는 메소드
     * @param memberId
     * @return
     */
    @Override
    public SuccessResponse<UserKeyRes> getUserKey(Long memberId){
        // member 조회
        String userKey = userRepository.findUserKeyByMemberId(memberId)
                        .orElseThrow(() -> new AppException(NOT_FOUND_MEMBER));

        // 반환
        UserKeyRes response = new UserKeyRes(userKey);
        return new SuccessResponse<>(GET_USER_KEY_SUCCESS, response);
    }


    /**
     * 금융망 API에 유저를 등록하는 메소드
     * @param phoneNumber
     * @return
     */
    public String registerFintechMember(String phoneNumber){
        try {
            return fintechService.createMember(phoneNumber + EMAIL_SUFFIX).getUserKey();
        } catch (Exception e) {
            // E4002: 이미 존재하는 ID일 때
            if (FINTECH_API_ID_ALREADY_EXISTS.getMessage().equals(e.getMessage())) {
                // 이미 존재하는 ID라면 searchMember 호출
                return fintechService.searchMember(phoneNumber + EMAIL_SUFFIX).getUserKey();
            } else {
                // 그 외 예외는 다시 던짐
                throw e;
            }
        }
    }


    /**
     * 회원 및 임시회원을 등록하는 메소드
     * @param request
     * @param UserKey
     */
    @Transactional
    public void registerUser(UserRegisterReq request, String UserKey){
        // Member 생성
        Member savedMember = memberService.registerMember(request.memberRegisterReq(), Role.TEMP_USER);

        // User 생성
        User user = userMapper.toUser(request, savedMember, UserKey);

        // 저장
        userRepository.save(user);
    }

}
