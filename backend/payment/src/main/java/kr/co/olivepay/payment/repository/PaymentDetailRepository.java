package kr.co.olivepay.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.entity.enums.PaymentState;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {

	List<PaymentDetail> findAllByPaymentId(Long paymentId);

	@Modifying
	@Transactional
	@Query("UPDATE PaymentDetail pd SET pd.paymentDetailState = :paymentState WHERE pd.id = :id")
	void updatePaymentDetailState(Long id, PaymentState paymentState);

}
