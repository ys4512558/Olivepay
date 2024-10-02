package kr.co.olivepay.funding.openapi.dto.res.account.rec;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransferRec {

    @JsonProperty("transactionUniqueNo")
    private String transactionUniqueNo;

    @JsonProperty("accountNo")
    private String accountNo;

    @JsonProperty("transactionDate")
    private String transactionDate;

    @JsonProperty("transactionType")
    private String transactionType;

    @JsonProperty("transactionTypeName")
    private String transactionTypeName;

    @JsonProperty("transactionAccountNo")
    private String transactionAccountNo;

}
