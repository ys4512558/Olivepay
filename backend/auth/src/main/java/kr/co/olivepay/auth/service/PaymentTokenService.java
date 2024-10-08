package kr.co.olivepay.auth.service;

import kr.co.olivepay.auth.dto.res.PaymentTokenRes;
import kr.co.olivepay.auth.global.response.SuccessResponse;

public interface PaymentTokenService {
    SuccessResponse<PaymentTokenRes> getPaymentToken(Long memberId);
}
