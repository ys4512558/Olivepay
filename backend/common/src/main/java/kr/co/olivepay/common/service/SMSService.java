package kr.co.olivepay.common.service;

import jakarta.validation.Valid;
import kr.co.olivepay.common.dto.req.SMSCheckReq;
import kr.co.olivepay.common.dto.req.SMSReq;
import kr.co.olivepay.common.global.enums.NoneResponse;
import kr.co.olivepay.common.global.response.SuccessResponse;

public interface SMSService {
    /**
     * 인증번호를 전송하는 메소드
     * @param request 전화번호가 담긴 request DTO {@link SMSReq}
     * @return 성곰 메시지 {@link NoneResponse}
     */
    SuccessResponse<NoneResponse> sendSMS(@Valid SMSReq request);

    /**
     * 인증번호를 검증하는 메소드
     * @param request 전화번호와 인증번호가 담긴 request DTO {@link SMSCheckReq}
     * @return 성공 메시지 {@link NoneResponse}
     */
    SuccessResponse<NoneResponse> verifyCode(SMSCheckReq request);
}
