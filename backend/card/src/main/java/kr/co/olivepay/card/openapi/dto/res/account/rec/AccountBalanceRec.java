package kr.co.olivepay.card.openapi.dto.res.account.rec;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalanceRec {

    @JsonProperty("bankCode")
    private String bankCode;
    @JsonProperty("accountNo")
    private String accountNo;
    @JsonProperty("accountBalance")
    private String accountBalance;
    @JsonProperty("accountCreatedDate")
    private String accountCreatedDate;
    @JsonProperty("accountExpiryDate")
    private String accountExpiryDate;
    @JsonProperty("currency")
    private String currency;

}
