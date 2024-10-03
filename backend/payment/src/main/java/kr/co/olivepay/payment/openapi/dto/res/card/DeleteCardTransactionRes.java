package kr.co.olivepay.payment.openapi.dto.res.card;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.olivepay.payment.openapi.dto.res.abstracts.FintechResponse;
import kr.co.olivepay.payment.openapi.dto.res.card.rec.DeleteCardTransactionRec;
import lombok.Data;

@Data
public class DeleteCardTransactionRes extends FintechResponse {
	@JsonProperty("REC")
	private DeleteCardTransactionRec REC;
}
