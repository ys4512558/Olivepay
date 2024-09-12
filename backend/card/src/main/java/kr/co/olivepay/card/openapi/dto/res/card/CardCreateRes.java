package kr.co.olivepay.card.openapi.dto.res.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.olivepay.card.openapi.dto.res.abstracts.FintechResponse;
import lombok.Data;

@Data
public class CardCreateRes extends FintechResponse {

    @JsonProperty("REC")
    private CardRec REC;

}
