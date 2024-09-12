package kr.co.olivepay.card.openapi.service;

import kr.co.olivepay.card.global.properties.FintechProperties;
import kr.co.olivepay.card.global.utils.FinTechHeaderGenerator;
import kr.co.olivepay.card.global.utils.FintechRestTemplateUtils;
import kr.co.olivepay.card.openapi.dto.req.FintechHeaderReq;
import kr.co.olivepay.card.openapi.dto.req.abstracts.FintechRequest;
import kr.co.olivepay.card.openapi.dto.req.account.AccountCreateReq;
import kr.co.olivepay.card.openapi.dto.req.card.CardCreateReq;
import kr.co.olivepay.card.openapi.dto.res.account.AccountCreateRes;
import kr.co.olivepay.card.openapi.dto.res.account.AccountRec;
import kr.co.olivepay.card.openapi.dto.res.card.CardCreateRes;
import kr.co.olivepay.card.openapi.dto.res.card.CardRec;
import kr.co.olivepay.card.openapi.enums.FintechRequestURI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class FintechServiceImpl implements FintechService {

    @Value("${config.fintech.apiNames.createAccount}")
    private String createAccountAPIName;
    @Value("${config.fintech.apiNames.createCard}")
    private String createCardAPIName;

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
        FintechHeaderReq header = finTechHeaderGenerator.generateFintechAPIHeader(createAccountAPIName, userKey);
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
     * @param userKey:             유저 키
     * @param withdrawalAccountNo: 출금 계좌
     * @return 생성된 카드 정보
     */
    @Override
    public CardRec createCard(String userKey, String withdrawalAccountNo) {
        //API 요청시 Body에 들어가는 "Header":{}
        FintechHeaderReq header = finTechHeaderGenerator.generateFintechAPIHeader(createCardAPIName, userKey);
        //실제 요청 Body에 들어갈 요청 파라미터들 생성
        FintechRequest request = CardCreateReq.builder()
                                              .Header(header)
                                              .cardUniqueNo(fintechProperties.getCardUniqueNo())
                                              .withdrawalAccountNo(withdrawalAccountNo)
                                              .build();

        //요청하려는 URI 생성 API 서버 주소 + 요청 URL
        URI uri = generateURI(FintechRequestURI.CREATE_CARD);
        //유틸 클래스를 통해 API 호출 및 응답코드 반환
        CardCreateRes cardCreateRes = restTemplateUtils.postForEntity(uri, request, CardCreateRes.class);

        return cardCreateRes.getREC();
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
