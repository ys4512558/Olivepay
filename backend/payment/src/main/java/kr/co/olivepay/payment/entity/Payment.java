package kr.co.olivepay.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.co.olivepay.payment.entity.enums.PaymentState;
import kr.co.olivepay.payment.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

	@Id
	@Column(name = "payment_id", nullable = false, columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//결제 주체
	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long memberId;

	//목적지
	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long franchiseId;

	//상태
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentState paymentState;

	//실패 시 원인
	private String failureReason;

	@Builder
	public Payment(Long id, Long memberId, Long franchiseId, PaymentState paymentState, String failureReason) {
		this.id = id;
		this.memberId = memberId;
		this.franchiseId = franchiseId;
		this.paymentState = paymentState;
		this.failureReason = failureReason;
	}
}
