package kr.co.olivepay.payment.repository;

import java.time.LocalDateTime;
import java.util.List;

import kr.co.olivepay.payment.entity.Payment;

public interface PaymentRepositoryCustom {

	List<Payment> findRecentSuccessfulPaymentsByMemberId(Long memberId, LocalDateTime startDate);
}
