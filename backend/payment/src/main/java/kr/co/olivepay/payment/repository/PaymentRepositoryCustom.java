package kr.co.olivepay.payment.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepositoryCustom {

	List<Long> findRecentSuccessfulPaymentIdsByMemberId(Long memberId, LocalDateTime startDate);

}
