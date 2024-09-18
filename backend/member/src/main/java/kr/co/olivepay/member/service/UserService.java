package kr.co.olivepay.member.service;

import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.SuccessResponse;

public interface UserService {

    SuccessResponse<NoneResponse> registerUser(UserRegisterReq request);
}
