package kr.co.olivepay.payment.service;

import kr.co.olivepay.core.global.dto.res.PageResponse;
import kr.co.olivepay.payment.dto.req.PaymentCreateReq;
import kr.co.olivepay.payment.dto.res.PaymentHistoryFranchiseRes;
import kr.co.olivepay.payment.dto.res.PaymentHistoryRes;
import kr.co.olivepay.payment.dto.res.PaymentKeyRes;
import kr.co.olivepay.payment.dto.res.PaymentMinimalRes;
import kr.co.olivepay.payment.global.response.SuccessResponse;

import java.util.List;

public interface PaymentService {

	SuccessResponse<PaymentKeyRes> pay(Long memberId, PaymentCreateReq request);

	SuccessResponse<PageResponse<List<PaymentHistoryFranchiseRes>>> getUserPaymentHistory(Long memberId, Long lastPaymentId);

	SuccessResponse<PageResponse<List<PaymentHistoryRes>>> getFranchisePaymentHistory(Long memberId, Long franchiseId, Long lastPaymentId);

	SuccessResponse<List<PaymentMinimalRes>> getRecentPaymentIds(Long memberId);
}
