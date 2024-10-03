package kr.co.olivepay.payment.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.olivepay.payment.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByMemberIdOrderByIdDesc(Long memberId, PageRequest of);

	List<Payment> findByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, Long lastPaymentId, PageRequest of);
}
