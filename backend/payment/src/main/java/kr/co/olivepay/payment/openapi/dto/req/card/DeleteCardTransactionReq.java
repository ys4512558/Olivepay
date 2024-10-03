package kr.co.olivepay.payment.openapi.dto.req.card;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.olivepay.payment.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.payment.openapi.dto.req.abstracts.FintechRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteCardTransactionReq extends FintechRequest {

	@JsonProperty("cardNo")
	private String cardNo;

	@JsonProperty("cvc")
	private String cvc;

	@JsonProperty("transactionUniqueNo")
	private Long transactionUniqueNo;

	@Builder
	public DeleteCardTransactionReq(FintechHeaderReq Header, String cardNo,
		String cvc, Long transactionUniqueNo) {
		super(Header);
		this.cardNo = cardNo;
		this.cvc = cvc;
		this.transactionUniqueNo = transactionUniqueNo;
	}

}
