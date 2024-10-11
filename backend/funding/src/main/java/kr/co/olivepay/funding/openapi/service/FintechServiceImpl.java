package kr.co.olivepay.funding.openapi.service;

import kr.co.olivepay.funding.global.properties.FintechProperties;
import kr.co.olivepay.funding.global.utils.FinTechHeaderGenerator;
import kr.co.olivepay.funding.global.utils.FintechRestTemplateUtils;
import kr.co.olivepay.funding.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.funding.openapi.dto.req.abstracts.FintechRequest;
import kr.co.olivepay.funding.openapi.dto.req.account.AccountTransferReq;
import kr.co.olivepay.funding.openapi.dto.res.account.AccountTransferRes;
import kr.co.olivepay.funding.openapi.dto.res.account.rec.AccountTransferRec;
import kr.co.olivepay.funding.openapi.enums.FintechRequestURI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FintechServiceImpl implements FintechService {

	private final String TRANSFER_ACCOUNT_API_NAME = "updateDemandDepositAccountTransfer";

	private final FintechProperties fintechProperties;
	private final FintechRestTemplateUtils restTemplateUtils;
	private final FinTechHeaderGenerator finTechHeaderGenerator;

	/**
	 * 계좌 이체
	 * @param userKey
	 * @param depositAccountNo 입금 계좌 번호
	 * @param transactionBalance 금액
	 * @param withdrawalAccountNo 출금 계좌 번호
	 * @param depositTransactionSummary
	 * @param withdrawalTransactionSummary
	 * @return
	 */
	@Override
	public List<AccountTransferRec> transferAccount(
		String userKey,
		String depositAccountNo,
		String transactionBalance,
		String withdrawalAccountNo,
		String depositTransactionSummary,
		String withdrawalTransactionSummary) {

		FintechHeaderReq header = finTechHeaderGenerator.generateFintechAPIHeader(TRANSFER_ACCOUNT_API_NAME, userKey);

		FintechRequest request = AccountTransferReq.builder()
												   .Header(header)
												   .depositAccountNo(depositAccountNo)
												   .transactionBalance(transactionBalance)
												   .withdrawalAccountNo(withdrawalAccountNo)
												   .depositTransactionSummary(depositTransactionSummary)
												   .withdrawalTransactionSummary(withdrawalTransactionSummary)
												   .build();

		URI uri = generateURI(FintechRequestURI.TRANSFER_ACCOUNT);

		AccountTransferRes accountTransferRes = restTemplateUtils.postForEntity(uri, request, AccountTransferRes.class);

		return accountTransferRes.getREC();
	}

	/**
	 * 요청하고자 하는 URI를 만들어주는 메서드
	 *
	 * @param requestURI
	 * @return 핀테크 API 요청을 위한 URI
	 */
	private URI generateURI(FintechRequestURI requestURI) {
		StringBuilder uriStringBuilder = new StringBuilder(fintechProperties.getFintechURI());
		uriStringBuilder.append(requestURI.getUri());
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(uriStringBuilder.toString())
														  .buildAndExpand();
		return uriComponents.toUri();
	}

}
