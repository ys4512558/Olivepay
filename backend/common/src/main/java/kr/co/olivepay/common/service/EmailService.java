package kr.co.olivepay.common.service;

import kr.co.olivepay.core.common.dto.req.EmailReq;
import kr.co.olivepay.common.global.enums.NoneResponse;
import kr.co.olivepay.common.global.response.SuccessResponse;

public interface EmailService {
    SuccessResponse<NoneResponse> send(EmailReq request);
}
