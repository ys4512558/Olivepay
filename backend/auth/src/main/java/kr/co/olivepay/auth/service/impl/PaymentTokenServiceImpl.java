package kr.co.olivepay.auth.service.impl;

import kr.co.olivepay.auth.dto.res.PaymentTokenRes;
import kr.co.olivepay.auth.enums.Role;
import kr.co.olivepay.auth.global.response.SuccessResponse;
import kr.co.olivepay.auth.global.utils.TokenGenerator;
import kr.co.olivepay.auth.service.PaymentTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;

import static kr.co.olivepay.auth.global.enums.SuccessCode.GET_PAYMENT_TOKEN_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTokenServiceImpl implements PaymentTokenService {

    private final TokenGenerator tokenGenerator;
    private final Role USER = Role.USER;
    private final Duration PAYMENT_TOKEN_EXPIRATION = Duration.ofMinutes(1);


    /**
     * 유저 memberId를 이용해 결제 토큰 생성 <br>
     * 유효기간 : PAYMENT_TOKEN_EXPIRATION
     * @param memberId
     * @return
     */
    @Override
    public SuccessResponse<PaymentTokenRes> getPaymentToken(Long memberId) {
        String paymentToken =
                tokenGenerator.generateToken(new HashMap<>(), memberId, USER, PAYMENT_TOKEN_EXPIRATION);

        PaymentTokenRes response = PaymentTokenRes.builder()
                                                  .paymentToken(paymentToken)
                                                  .build();

        return new SuccessResponse<>(GET_PAYMENT_TOKEN_SUCCESS, response);
    }
}
