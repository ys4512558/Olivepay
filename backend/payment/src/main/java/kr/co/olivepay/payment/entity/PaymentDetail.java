package kr.co.olivepay.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.co.olivepay.payment.entity.enums.PaymentState;
import kr.co.olivepay.payment.entity.enums.PaymentType;
import kr.co.olivepay.payment.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentDetail extends BaseEntity {

	@Id
	@Column(name = "payment_detail_id", nullable = false, columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//결제 ID
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id", nullable = false, columnDefinition = "INT UNSIGNED")
	private Payment payment;

	//결제 금액
	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long amount;

	//결제 타입
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	//결제 매체 이름
	@Column(nullable = false)
	private String paymentName;

	//카드 ID
	@Column(name = "payment_type_id", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long paymentTypeId;

	//결제
	@Column(name = "payment_detail_state", nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentState paymentDetailState;

	@Builder
	public PaymentDetail(Long id, Payment payment, Long amount, PaymentType paymentType, String paymentName, Long paymentTypeId,
		PaymentState paymentDetailState) {
		this.id = id;
		this.payment = payment;
		this.amount = amount;
		this.paymentType = paymentType;
		this.paymentName = paymentName;
		this.paymentTypeId = paymentTypeId;
		this.paymentDetailState = paymentDetailState;
	}

	public void updatePaymentDetailState(PaymentState paymentDetailState){
		this.paymentDetailState = paymentDetailState;
	}
}
