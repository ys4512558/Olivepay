package kr.co.olivepay.payment.service;

import java.util.List;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.payment.dto.req.PaymentCreateReq;
import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.PaymentDetail;

public interface PaymentDetailService {

	List<PaymentDetail> getPaymentDetails(Long paymentId);

	List<PaymentDetail> createPaymentDetails(Payment payment, Long amount, Long couponUnit,
		List<PaymentCardSearchRes> cardList);


}
