package kr.co.olivepay.payment.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.entity.enums.PaymentState;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByMemberIdOrderByIdDesc(Long memberId, PageRequest of);

	List<Payment> findByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, Long lastPaymentId, PageRequest of);

	List<Payment> findByFranchiseIdOrderByIdDesc(Long franchiseId, PageRequest of);

	List<Payment> findByFranchiseIdAndIdLessThanOrderByIdDesc(Long franchiseId, Long lastPaymentId, PageRequest of);

	@Modifying
	@Transactional
	@Query("UPDATE Payment pd SET pd.paymentState = :paymentState WHERE pd.id = :id")
	void updatePaymentState(Long id, PaymentState paymentState);

}
