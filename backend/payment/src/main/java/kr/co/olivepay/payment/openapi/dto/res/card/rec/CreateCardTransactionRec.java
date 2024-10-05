package kr.co.olivepay.payment.openapi.dto.res.card.rec;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardTransactionRec {

	@JsonProperty("transactionUniqueNo")
	private String transactionUniqueNo;

	@JsonProperty("categoryId")
	private String categoryId;

	@JsonProperty("categoryName")
	private String categoryName;

	@JsonProperty("merchantId")
	private Long merchantId;

	@JsonProperty("merchantName")
	private String merchantName;

	@JsonProperty("transactionDate")
	private String transactionDate;

	@JsonProperty("transactionTime")
	private String transactionTime;

	@JsonProperty("transactionBalance")
	private Long transactionBalance;

}
