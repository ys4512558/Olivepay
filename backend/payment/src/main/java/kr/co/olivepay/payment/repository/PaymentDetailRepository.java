package kr.co.olivepay.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.olivepay.payment.entity.PaymentDetail;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {

	List<PaymentDetail> findAllByPaymentId(Long paymentId);
}
