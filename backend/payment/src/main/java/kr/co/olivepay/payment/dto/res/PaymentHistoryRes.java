package kr.co.olivepay.payment.dto.res;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * 가맹점 결제 내역을 위한 dto
 */
@Getter
@SuperBuilder
public class PaymentHistoryRes {
	Long paymentId;
	Long amount;
	LocalDateTime createdAt;
	List<PaymentDetailRes> details;

}