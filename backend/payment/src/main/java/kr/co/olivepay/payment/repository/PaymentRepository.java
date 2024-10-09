package kr.co.olivepay.payment.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.enums.PaymentState;
import kr.co.olivepay.payment.global.enums.ErrorCode;
import kr.co.olivepay.payment.global.handler.AppException;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryCustom {

	List<Payment> findByMemberIdAndPaymentStateOrderByIdDesc(Long memberId, PaymentState paymentState, PageRequest pageRequest);
	List<Payment> findByMemberIdAndPaymentStateAndIdLessThanOrderByIdDesc(Long memberId, PaymentState paymentState, Long lastPaymentId, PageRequest pageRequest);

	List<Payment> findByFranchiseIdAndPaymentStateOrderByIdDesc(Long franchiseId, PaymentState paymentState, PageRequest of);
	List<Payment> findByFranchiseIdAndPaymentStateAndIdLessThanOrderByIdDesc(Long franchiseId, PaymentState paymentState, Long lastPaymentId, PageRequest of);

	default Payment getById(Long id) {
		return findById(id).orElseThrow(()->new AppException(ErrorCode.PAYMENT_HISTORY_NOT_FOUND_BY_ID));
	}

}
