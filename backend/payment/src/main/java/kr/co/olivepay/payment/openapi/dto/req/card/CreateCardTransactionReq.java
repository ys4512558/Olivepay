package kr.co.olivepay.payment.openapi.dto.req.card;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.olivepay.payment.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.payment.openapi.dto.req.abstracts.FintechRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCardTransactionReq extends FintechRequest {

	@JsonProperty("cardNo")
	private String cardNo;

	@JsonProperty("cvc")
	private String cvc;

	@JsonProperty("merchantId")
	private Long merchantId;

	@JsonProperty("paymentBalance")
	private Long paymentBalance;

	@Builder
	public CreateCardTransactionReq(FintechHeaderReq Header, String cardNo, String cvc, Long merchantId,
		Long paymentBalance) {
		super(Header);
		this.cardNo = cardNo;
		this.cvc = cvc;
		this.merchantId = merchantId;
		this.paymentBalance = paymentBalance;
	}
}
