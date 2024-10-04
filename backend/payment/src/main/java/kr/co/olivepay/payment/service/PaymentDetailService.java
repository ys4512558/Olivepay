package kr.co.olivepay.payment.service;

import java.util.List;

import kr.co.olivepay.payment.entity.PaymentDetail;

public interface PaymentDetailService {

	List<PaymentDetail> getPaymentDetails(Long paymentId);

}
