package kr.co.olivepay.payment.repository.impl;

import java.time.LocalDateTime;
import java.util.List;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.olivepay.payment.entity.QPayment;
import kr.co.olivepay.payment.entity.enums.PaymentState;
import kr.co.olivepay.payment.repository.PaymentRepositoryCustom;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Long> findRecentSuccessfulPaymentIdsByMemberId(Long memberId, LocalDateTime startDate) {
		QPayment payment = QPayment.payment;

		return queryFactory.select(payment.id)
			.from(payment)
			.where(payment.memberId.eq(memberId)
				.and(payment.paymentState.eq(PaymentState.SUCCESS))
				.and(payment.createdAt.goe(startDate))).fetch();
	}
}
