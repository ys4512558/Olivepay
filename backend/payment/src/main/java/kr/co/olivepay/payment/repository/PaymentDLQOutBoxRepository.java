package kr.co.olivepay.payment.repository;

import kr.co.olivepay.payment.entity.PaymentDLQOutBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDLQOutBoxRepository extends JpaRepository<PaymentDLQOutBox, Long> {

}
