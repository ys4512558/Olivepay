package kr.co.olivepay.card.openapi.service;

import kr.co.olivepay.card.global.properties.CardUniqueNoProperties;
import kr.co.olivepay.card.global.properties.FintechProperties;
import kr.co.olivepay.card.global.utils.FinTechHeaderGenerator;
import kr.co.olivepay.card.global.utils.FintechRestTemplateUtils;
import kr.co.olivepay.card.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.card.openapi.dto.req.abstracts.FintechRequest;
import kr.co.olivepay.card.openapi.dto.req.account.AccountBalanceCheckReq;
import kr.co.olivepay.card.openapi.dto.req.account.AccountCreateReq;
import kr.co.olivepay.card.openapi.dto.req.account.AccountDepositReq;
import kr.co.olivepay.card.openapi.dto.req.card.CardCreateReq;
import kr.co.olivepay.card.openapi.dto.res.account.AccountBalanceRes;
import kr.co.olivepay.card.openapi.dto.res.account.AccountCreateRes;
import kr.co.olivepay.card.openapi.dto.res.account.AccountDepositRes;
import kr.co.olivepay.card.openapi.dto.res.account.rec.AccountBalanceRec;
import kr.co.olivepay.card.openapi.dto.res.account.rec.AccountDepositRec;
import kr.co.olivepay.card.openapi.dto.res.account.rec.AccountRec;
import kr.co.olivepay.card.openapi.dto.res.card.CardCreateRes;
import kr.co.olivepay.card.openapi.dto.res.card.rec.CardRec;
import kr.co.olivepay.card.openapi.enums.FintechRequestURI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class FintechServiceImpl implements FintechService {

    private final String CREATE_ACCOUNT_API_NAME = "createDemandDepositAccount";
    private final String CREATE_CARD_API_NAME = "createCreditCard";
    private final String DEPOSIT_ACCOUNT_API_NAME = "updateDemandDepositAccountDeposit";
    private final String GET_ACCOUNT_BALANCE_API_NAME = "inquireDemandDepositAccountBalance";

    private final CardUniqueNoProperties cardUniqueNoProperties;
    private final FintechProperties fintechProperties;
    private final FintechRestTemplateUtils restTemplateUtils;
    private final FinTechHeaderGenerator finTechHeaderGenerator;

    /**
     * 계좌 생성을 위한 서비스 로직
     *
     * @param userKey: 유저 키
     * @return 생성된 계좌 정보
     */
    @Override
    public AccountRec createAccount(String userKey) {
        //API 요청시 Body에 들어가는 "Header":{}
        FintechHeaderReq header = finTechHeaderGenerator.generateFintechAPIHeader(CREATE_ACCOUNT_API_NAME, userKey);
        //실제 요청 Body에 들어갈 요청 파라미터들 생성
        FintechRequest request = AccountCreateReq.builder()
                                                 .Header(header)
                                                 .accountTypeUniqueNo(fintechProperties.getAccountTypeUniqueNo())
                                                 .build();

        //요청하려는 URI 생성 API 서버 주소 + 요청 URL
        URI uri = generateURI(FintechRequestURI.CREATE_ACCOUNT);
        //유틸 클래스를 통해 API 호출 및 응답코드 반환
        AccountCreateRes accountCreateRes = restTemplateUtils.postForEntity(uri, request, AccountCreateRes.class);

        return accountCreateRes.getREC();
    }

    /**
     * 카드 생성을 위한 서비스 로직
     *
     * @param userKey             : 유저 키
     * @param withdrawalAccountNo : 출금 계좌
     * @param cardCompanyName     : 카드사 이름
     * @return 생성된 카드 정보
     */
    @Override
    public CardRec createCard(String userKey, String withdrawalAccountNo, String cardCompanyName) {
        //API 요청시 Body에 들어가는 "Header":{}
        FintechHeaderReq header = finTechHeaderGenerator.generateFintechAPIHeader(CREATE_CARD_API_NAME, userKey);
        //실제 요청 Body에 들어갈 요청 파라미터들 생성
        FintechRequest request = CardCreateReq.builder()
                                              .Header(header)
                                              .cardUniqueNo(cardUniqueNoProperties.getCardProduct(cardCompanyName))
                                              .withdrawalAccountNo(withdrawalAccountNo)
                                              .build();

        //요청하려는 URI 생성 API 서버 주소 + 요청 URL
        URI uri = generateURI(FintechRequestURI.CREATE_CARD);
        //유틸 클래스를 통해 API 호출 및 응답코드 반환
        CardCreateRes cardCreateRes = restTemplateUtils.postForEntity(uri, request, CardCreateRes.class);

        return cardCreateRes.getREC();
    }

    /**
     * 계좌 입금 서비스
     *
     * @param userKey            : 유저 키
     * @param accountNo          : 입금 계좌 번호
     * @param transactionBalance : 입금액
     * @param transactionSummary : 입금 메모
     * @return
     */
    @Override
    public AccountDepositRec depositAccount(String userKey, String accountNo, String transactionBalance, String transactionSummary) {
        //API 요청시 Body에 들어가는 "Header":{}
        FintechHeaderReq header = finTechHeaderGenerator.generateFintechAPIHeader(DEPOSIT_ACCOUNT_API_NAME, userKey);
        //실제 요청 Body에 들어갈 요청 파라미터들 생성
        FintechRequest request = AccountDepositReq.builder()
                                                  .Header(header)
                                                  .accountNo(accountNo)
                                                  .transactionBalance(transactionBalance)
                                                  .transactionSummary(transactionSummary)
                                                  .build();

        //요청하려는 URI 생성 API 서버 주소 + 요청 URL
        URI uri = generateURI(FintechRequestURI.DEPOSIT_ACCOUNT);
        //유틸 클래스를 통해 API 호출 및 응답코드 반환
        AccountDepositRes accountDepositRes = restTemplateUtils.postForEntity(uri, request, AccountDepositRes.class);

        return accountDepositRes.getREC();
    }

    /**
     * 계좌 잔액 조회
     * @param userKey : 유저 키
     * @param accountNo : 계좌 번호
     * @return
     */
    @Override
    public AccountBalanceRec getAccountBalance(String userKey, String accountNo) {
        FintechHeaderReq header = finTechHeaderGenerator.generateFintechAPIHeader(GET_ACCOUNT_BALANCE_API_NAME, userKey);
        AccountBalanceCheckReq request = AccountBalanceCheckReq.builder()
                                                               .Header(header)
                                                               .accountNo(accountNo)
                                                               .build();

        URI uri = generateURI(FintechRequestURI.CHECK_ACCOUNT_BALANCE);
        AccountBalanceRes accountBalanceRes = restTemplateUtils.postForEntity(uri, request, AccountBalanceRes.class);
        return accountBalanceRes.getREC();
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
