package kr.co.olivepay.member.service;

import kr.co.olivepay.core.member.dto.req.UserPinCheckReq;
import kr.co.olivepay.core.member.dto.res.UserKeyRes;
import kr.co.olivepay.member.dto.req.UserPasswordChangeReq;
import kr.co.olivepay.member.dto.req.UserPasswordCheckReq;
import kr.co.olivepay.member.dto.res.UserPasswordCheckRes;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.SuccessResponse;

public interface UserInfoService {

    SuccessResponse<UserPasswordCheckRes> checkUserPassword(
            Long memberId, UserPasswordCheckReq request
    );

    SuccessResponse<NoneResponse> changeUSerPassword(
            Long memberId, UserPasswordChangeReq request
    );

    SuccessResponse<UserKeyRes> checkUserPin(
            Long memberId, UserPinCheckReq request
    );
}
