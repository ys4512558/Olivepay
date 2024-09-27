package kr.co.olivepay.card.openapi.dto.res.account.rec;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 계좌 생성 시 REC 필드를 통해 반환되는 객체 값
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountRec {

    @JsonProperty("bankCode")
    private String bankCode;
    @JsonProperty("accountNo")
    private String accountNo;
    @JsonProperty("currency")
    private Currency currency;

}
