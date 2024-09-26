package kr.co.olivepay.card.openapi.dto.res.account.rec;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountDepositRec {

    @JsonProperty("transactionUniqueNo")
    private String transactionUniqueNo;

    @JsonProperty("transactionDate")
    private String transactionDate;

}
