package kr.co.olivepay.funding.openapi.dto.req.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import kr.co.olivepay.funding.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.funding.openapi.dto.req.abstracts.FintechRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountTransferReq extends FintechRequest {

	@JsonProperty("depositAccountNo")
	private String depositAccountNo;

	@JsonProperty("transactionBalance")
	private String transactionBalance;

	@JsonProperty("withdrawalAccountNo")
	private String withdrawalAccountNo;

	@JsonProperty("depositTransactionSummary")
	private String depositTransactionSummary;

	@JsonProperty("withdrawalTransactionSummary")
	private String withdrawalTransactionSummary;

	@Builder
	public AccountTransferReq(FintechHeaderReq Header, String depositAccountNo, String transactionBalance,
		String withdrawalAccountNo, String depositTransactionSummary, String withdrawalTransactionSummary) {
		super(Header);
		this.depositAccountNo = depositAccountNo;
        this.transactionBalance = transactionBalance;
        this.withdrawalAccountNo = withdrawalAccountNo;
        this.depositTransactionSummary = depositTransactionSummary;
        this.withdrawalTransactionSummary = withdrawalTransactionSummary;
	}
}
