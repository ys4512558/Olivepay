package kr.co.olivepay.payment.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.repository.PaymentDetailRepository;
import kr.co.olivepay.payment.service.PaymentDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentDetailServiceImpl implements PaymentDetailService {

	private final PaymentDetailRepository paymentDetailRepository;

	@Override
	public List<PaymentDetail> getPaymentDetails(Long paymentId) {
		return paymentDetailRepository.findAllByPaymentId(paymentId);
	}
}
