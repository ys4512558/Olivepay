package kr.co.olivepay.payment.openapi.dto.res.card;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.olivepay.payment.openapi.dto.res.abstracts.FintechResponse;
import kr.co.olivepay.payment.openapi.dto.res.card.rec.CreateCardTransactionRec;
import lombok.Data;

@Data
public class CreateCardTransactionRes extends FintechResponse {
    @JsonProperty("REC")
    private CreateCardTransactionRec REC;
}
