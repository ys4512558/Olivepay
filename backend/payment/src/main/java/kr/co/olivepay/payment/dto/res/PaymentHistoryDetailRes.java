package kr.co.olivepay.payment.dto.res;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * 유저 결제 내역을 위한 dto
 */
@Getter
public class PaymentHistoryDetailRes extends PaymentHistoryRes {
	private final Long franchiseId;
	private final String franchiseName;

	@Builder(builderMethodName = "detailBuilder")
	public PaymentHistoryDetailRes(Long paymentId, Long amount, LocalDateTime createdAt,
		List<PaymentDetailRes> details, Long franchiseId, String franchiseName) {
		super(paymentId, amount, createdAt, details);
		this.franchiseId = franchiseId;
		this.franchiseName = franchiseName;
	}

	public static class PaymentHistoryDetailResBuilder {
		private Long paymentId;
		private Long amount;
		private LocalDateTime createdAt;
		private List<PaymentDetailRes> details;
		private Long franchiseId;
		private String franchiseName;

		PaymentHistoryDetailResBuilder() {
		}

		public PaymentHistoryDetailResBuilder paymentId(Long paymentId) {
			this.paymentId = paymentId;
			return this;
		}

		public PaymentHistoryDetailResBuilder amount(Long amount) {
			this.amount = amount;
			return this;
		}

		public PaymentHistoryDetailResBuilder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public PaymentHistoryDetailResBuilder details(List<PaymentDetailRes> details) {
			this.details = details;
			return this;
		}

		public PaymentHistoryDetailResBuilder franchiseId(Long franchiseId) {
			this.franchiseId = franchiseId;
			return this;
		}

		public PaymentHistoryDetailResBuilder franchiseName(String franchiseName) {
			this.franchiseName = franchiseName;
			return this;
		}

		public PaymentHistoryDetailRes build() {
			return new PaymentHistoryDetailRes(paymentId, amount, createdAt, details, franchiseId, franchiseName);
		}
	}
}