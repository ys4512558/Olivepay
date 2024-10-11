package kr.co.olivepay.payment.openapi.service;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import kr.co.olivepay.payment.global.properties.FintechProperties;
import kr.co.olivepay.payment.global.utils.FinTechHeaderGenerator;
import kr.co.olivepay.payment.global.utils.FintechRestTemplateUtils;
import kr.co.olivepay.payment.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.payment.openapi.dto.req.abstracts.FintechRequest;
import kr.co.olivepay.payment.openapi.dto.req.card.*;
import kr.co.olivepay.payment.openapi.dto.res.card.*;
import kr.co.olivepay.payment.openapi.dto.res.card.rec.CreateCardTransactionRec;
import kr.co.olivepay.payment.openapi.dto.res.card.rec.DeleteCardTransactionRec;
import kr.co.olivepay.payment.openapi.enums.FintechRequestURI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FintechServiceImpl implements FintechService {

	private final String CREATE_CARD_TRANSACTION_API_NAME = "createCreditCardTransaction";
	private final String CANCEL_CARD_TRANSACTION_API_NAME = "deleteTransaction";

	private final FintechProperties fintechProperties;
	private final FintechRestTemplateUtils restTemplateUtils;
	private final FinTechHeaderGenerator finTechHeaderGenerator;

	/**
	 * 카드 결제
	 * @param userKey 
	 * @param cardNo 카드번호
	 * @param cvc 카드보안코드
	 * @param merchantId 가맹점ID
	 * @param payementBalance 거래금액
	 * @return
	 */
	@Override
	public CreateCardTransactionRec processCardPayment(
		String userKey,
		String cardNo,
		String cvc,
		Long merchantId,
		Long payementBalance) {

		FintechHeaderReq header = finTechHeaderGenerator.generateFintechAPIHeader(CREATE_CARD_TRANSACTION_API_NAME,
			userKey);

		FintechRequest request = CreateCardTransactionReq.builder()
														 .Header(header)
														 .cardNo(cardNo)
														 .cvc(cvc)
														 .merchantId(merchantId)
														 .paymentBalance(payementBalance)
														 .build();

		URI uri = generateURI(FintechRequestURI.CREATE_CARD_TRANSACTION);
		CreateCardTransactionRes createCardTransactionRes = restTemplateUtils.postForEntity(uri, request,
			CreateCardTransactionRes.class);
		return createCardTransactionRes.getREC();
	}

	/**
	 * 카드 결제 취소
	 * @param userKey 
	 * @param cardNo 카드번호
	 * @param cvc 카드보안코드
	 * @param transactionUniqueNo 거래고유번호
	 * @return
	 */
	@Override
	public DeleteCardTransactionRec cancelCardPayment(
		String userKey,
		String cardNo,
		String cvc,
		Long transactionUniqueNo) {

		FintechHeaderReq header = finTechHeaderGenerator.generateFintechAPIHeader(CANCEL_CARD_TRANSACTION_API_NAME,
			userKey);

		FintechRequest request = CancelCardTransactionReq.builder()
														 .Header(header)
														 .cardNo(cardNo)
														 .cvc(cvc)
														 .transactionUniqueNo(transactionUniqueNo)
														 .build();

		URI uri = generateURI(FintechRequestURI.CANCEL_CARD_TRANSACTION);
		CancelCardTransactionRes cancelCardTransactionRes = restTemplateUtils.postForEntity(uri, request,
			CancelCardTransactionRes.class);
		return cancelCardTransactionRes.getREC();

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
