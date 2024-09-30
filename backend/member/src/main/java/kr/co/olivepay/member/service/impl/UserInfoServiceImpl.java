package kr.co.olivepay.member.service.impl;

import kr.co.olivepay.core.member.dto.req.UserPinCheckReq;
import kr.co.olivepay.core.member.dto.res.UserKeyRes;
import kr.co.olivepay.member.dto.req.UserInfoChangeReq;
import kr.co.olivepay.member.dto.req.UserPasswordChangeReq;
import kr.co.olivepay.member.dto.req.UserPasswordCheckReq;
import kr.co.olivepay.member.dto.req.UserPinChangeReq;
import kr.co.olivepay.member.dto.res.UserInfoRes;
import kr.co.olivepay.member.dto.res.UserPasswordCheckRes;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.entity.User;
import kr.co.olivepay.member.global.enums.ErrorCode;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.handler.AppException;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.mapper.UserMapper;
import kr.co.olivepay.member.repository.MemberRepository;
import kr.co.olivepay.member.repository.UserRepository;
import kr.co.olivepay.member.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kr.co.olivepay.member.global.enums.ErrorCode.*;
import static kr.co.olivepay.member.global.enums.SuccessCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /**
     * 유저 비밀번호 변경 전, 비밀번호를 검증하는 메소드
     * @param memberId
     * @param request
     * @return
     */
    @Override
    public SuccessResponse<UserPasswordCheckRes> checkUserPassword(
            Long memberId, UserPasswordCheckReq request)
    {
        Member member = memberRepository.getById(memberId);
        boolean isCorrect = passwordEncoder.matches(request.password(), member.getPassword());

        UserPasswordCheckRes response = new UserPasswordCheckRes(isCorrect);
        return new SuccessResponse<>(PASSWORD_CHECK_SUCCESS, response);
    }

    /**
     * 유저 비밀번호 변경을 위한 메소드
     * @param memberId
     * @param request
     * @return
     */
    @Override
    public SuccessResponse<NoneResponse> changeUSerPassword(
            Long memberId, UserPasswordChangeReq request)
    {
        Member member = memberRepository.getById(memberId);
        member.updatePassword(passwordEncoder.encode(request.password()));

        memberRepository.save(member);
        return new SuccessResponse<>(PASSWORD_CHANGE_SUCCESS, NoneResponse.NONE);
    }

    /**
     * 결제 전, 유저 간편 결제 비밀번호를 검증하는 메소드<br>
     * 2회 이하 실패: PIN_INVALID_1, PIN_INVALID_2 에러 <br> 
     * 3회 이상 실패: PIN_LOCKED 에러
     * @param memberId
     * @param request
     * @return
     */
    @Override
    public SuccessResponse<UserKeyRes> checkUserPin(Long memberId, UserPinCheckReq request) {
        User user = userRepository.getByMemberId(memberId);

        if(user.getPinCount()==3){
            throw new AppException(PIN_LOCKED);
        }

        // 간편결제 비밀번호 검증
        if(!user.getPin().equals(request.pin())) {
            user.increasePinCount();
            userRepository.save(user);

            switch (user.getPinCount()){
                case 1: throw new AppException(PIN_INVALID_ONE);
                case 2: throw new AppException(PIN_INVALID_TWO);
                case 3: throw new AppException(PIN_LOCKED);
            }
        }

        user.resetPinCount();
        userRepository.save(user);

        UserKeyRes response = new UserKeyRes(user.getUserKey());
        return new SuccessResponse<>(PIN_CHECK_SUCCESS, response);
    }

    /**
     * 유저의 간편 결제 비밀번호를 변경하는 메소드
     * @param memberId
     * @param request
     * @return
     */
    @Override
    public SuccessResponse<NoneResponse> changeUserPin(Long memberId, UserPinChangeReq request) {
        User user = userRepository.getByMemberId(memberId);

        user.setPin(request.pin());
        user.resetPinCount();
        userRepository.save(user);
        return new SuccessResponse<>(PIN_CHECK_SUCCESS, NoneResponse.NONE);
    }

    /**
     * 유저의 정보를 조회하는 메소드
     * @param memberId
     * @return
     */
    @Override
    public SuccessResponse<UserInfoRes> getUserInfo(Long memberId) {
        User user = userRepository.getByMemberId(memberId);

        UserInfoRes response = userMapper.toUserInfoRes(user);
        return new SuccessResponse<>(GET_MY_SUCCESS, response);
    }

    /**
     * 유저의 정보(닉네임)을 변경하는 메소드
     * @param memberId
     * @param request
     * @return
     */
    @Override
    public SuccessResponse<NoneResponse> modifyUserInfo(Long memberId, UserInfoChangeReq request) {
        User user = userRepository.getByMemberId(memberId);

        user.updateNickname(request.nickname());
        userRepository.save(user);

        return new SuccessResponse<>(UPDATE_MY_SUCCESS, NoneResponse.NONE);
    }
}
