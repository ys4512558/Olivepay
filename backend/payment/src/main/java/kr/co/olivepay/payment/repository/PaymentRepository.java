package kr.co.olivepay.payment.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.global.enums.ErrorCode;
import kr.co.olivepay.payment.global.handler.AppException;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByMemberIdOrderByIdDesc(Long memberId, PageRequest of);

	List<Payment> findByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, Long lastPaymentId, PageRequest of);

	List<Payment> findByFranchiseIdOrderByIdDesc(Long franchiseId, PageRequest of);

	List<Payment> findByFranchiseIdAndIdLessThanOrderByIdDesc(Long franchiseId, Long lastPaymentId, PageRequest of);

	default Payment getById(Long id) {
		return findById(id).orElseThrow(()->new AppException(ErrorCode.PAYMENT_HISTORY_NOT_FOUND_BY_ID));
	}

}
