package kr.co.olivepay.common.service;

import kr.co.olivepay.core.common.dto.req.EmailReq;
import kr.co.olivepay.common.global.enums.NoneResponse;
import kr.co.olivepay.common.global.response.SuccessResponse;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    /**
     * 후원자에게 이메일 보내기 메소드
     *
     * @param request 이메일, 후원 내역 리스트가 담긴 DTO {@link EmailReq}
     * @return 이메일 보내기 성공 메시지
     */
    CompletableFuture<SuccessResponse<NoneResponse>> send(EmailReq request);
}
