package kr.co.olivepay.card.service;

import jakarta.annotation.PostConstruct;
import kr.co.olivepay.card.dto.req.CardRegisterReq;
import kr.co.olivepay.card.dto.req.CardSearchReq;
import kr.co.olivepay.card.dto.res.MyCardSearchRes;
import kr.co.olivepay.card.dto.res.TransactionCardSearchRes;
import kr.co.olivepay.card.entity.Account;
import kr.co.olivepay.card.entity.Card;
import kr.co.olivepay.card.entity.CardCompany;
import kr.co.olivepay.card.global.handler.AppException;
import kr.co.olivepay.card.mapper.AccountMapper;
import kr.co.olivepay.card.mapper.CardMapper;
import kr.co.olivepay.card.openapi.dto.res.account.AccountRec;
import kr.co.olivepay.card.openapi.dto.res.card.CardRec;
import kr.co.olivepay.card.openapi.service.FintechService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static kr.co.olivepay.card.global.enums.ErrorCode.CARDCOMPANY_NOT_EXIST;
import static kr.co.olivepay.card.global.enums.ErrorCode.CARD_DUPLICATE;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final FintechService fintechService;
    private final CardTransactionService cardTransactionService;
    private final AccountMapper accountMapper;
    private final CardMapper cardMapper;
    private Map<String, String> cardCompanyMap;

    /**
     * 사용자가 카드를 등록합니다.
     * 1. 카드 번호 중복 체크
     * 금융 API 로직 처리 시작
     * 2. 계좌 서비스를 통해 계좌 생성 요청
     * 3. 계좌 생성 완료 시 받은 계좌번호를 통해 카드 생성
     * 금융 API 로직 처리 끝
     * 4. DB에 카드 등록
     *
     * @param memberId
     * @param cardRegisterReq
     * @return 등록된 카드 반환
     */
    @Override
    public Card registerCard(
            final Long memberId, final String userKey,
            CardRegisterReq cardRegisterReq) {
        //중복 체크
        String realCardNumber = cardRegisterReq.realCardNumber();
        checkDuplicateCardNumber(realCardNumber);

        //계좌 생성
        AccountRec accountRec = fintechService.createAccount(userKey);
        //카드 생성
        CardRec cardRec = fintechService.createCard(userKey, accountRec.getAccountNo());
        Account account = accountMapper.toEntity(accountRec);

        // 카드 앞 6자리 슬라이스
        String prefix6 = realCardNumber.substring(0, 6);
        // 카드 앞 4자리 슬라이스
        String prefix4 = realCardNumber.substring(0, 4);

        // 6자리 비교 -> 4자리 비교 -> 없으면 예외
        String cardCompanyName
                = cardCompanyMap.getOrDefault(prefix6, cardCompanyMap.getOrDefault(prefix4, null));
        if (cardCompanyName == null) {
            throw new AppException(CARDCOMPANY_NOT_EXIST);
        }

        // 이름으로 카드사 객체 가져오기
        CardCompany cardCompany = cardTransactionService.getCardCompany(cardCompanyName);
        Card card = cardMapper.toEntity(memberId, account, cardRec, cardRegisterReq, cardCompany);

        //DB에 등록
        return cardTransactionService.registerCard(account, card);
    }

    /**
     * 실제 카드 등록 시 중복되는지 확인하는 메서드
     *
     * @param realCardNumber
     */
    private void checkDuplicateCardNumber(String realCardNumber) {
        Optional<Card> optionalCard = cardTransactionService.getCard(realCardNumber);

        if (optionalCard.isPresent()) {
            throw new AppException(CARD_DUPLICATE);
        }
    }

    /**
     * 카드 삭제 메서드
     * @param memberId
     * @param cardId
     */
    @Override
    public void deleteCard(Long memberId, Long cardId) {
        cardTransactionService.deleteCard(memberId, cardId);
    }

    /**
     * 내 카드 목록 조회
     * @param memberId
     * @return 내가 등록한 카드 리스트
     */
    @Override
    public List<MyCardSearchRes> getMyCardList(Long memberId) {
        List<Card> cardList = cardTransactionService.getMyCardList(memberId);
        return cardList.stream()
                       .map(cardMapper::toMyCardSearchRes)
                       .toList();
    }

    /**
     * 결제용 카드 정보 리스트 반환
     *
     * @param memberId
     * @param cardSearchReq : 차액 결제 카드 ID, 공용 기부금 사용 여부
     * @return 결제용 카드 정보 리스트
     */
    @Override
    public List<TransactionCardSearchRes> getTransactionCardList(Long memberId, CardSearchReq cardSearchReq) {
        List<Card> cardList = cardTransactionService.getTransactionCardList(memberId, cardSearchReq);
        return cardList.stream()
                       .map(cardMapper::toTransactionCardSearchRes)
                       .toList();
    }

    /**
     * 카드사별 prefix를 Map에 저장하기 위한 초기화 메서드
     */
    @PostConstruct
    private void init() {
        cardCompanyMap = new HashMap<>();
        String[] BCCard = new String[]{
                "3560", "3563", "3565", "404825", "414025", "4481", "4553",
                "4906", "538159", "5388", "6048", "6360", "6210",
                "6253", "9410", "9420", "942150", "9430", "943150",
                "944603", "9460", "9461", "944403", "9440"
        };
        Arrays.stream(BCCard)
              .forEach((str) -> cardCompanyMap.put(str, "BC카드"));

        String[] KBCard = new String[]{
                "356407", "356415", "356416", "356417", "356910", "356911",
                "356912", "375144", "376364", "376364", "433290", "457972",
                "457973", "4673", "502123", "522971", "527289", "540926",
                "540947", "554346", "554959", "557042", "558526", "502123",
                "623374", "625804", "943645", "943646", "944541", "944542",
                "944547", "949094"
        };
        Arrays.stream(KBCard)
              .forEach((str) -> cardCompanyMap.put(str, "KB국민카드"));

        String[] NHCard = new String[]{
                "356316", "356317", "356418", "356516", "4092", "485479",
                "524040", "524041", "543000", "543017", "542416", "546111",
                "546112", "941116", "941117", "944116", "946316",
        };
        Arrays.stream(NHCard)
              .forEach((str) -> cardCompanyMap.put(str, "NH농협카드"));

        String[] LotteCard = new String[]{
                "356914", "356915", "356916", "376272", "376277", "377973",
                "421468", "464959", "467008", "488972", "512462", "513792",
                "625104", "625904", "940915", "940951"
        };
        Arrays.stream(LotteCard)
              .forEach((str) -> cardCompanyMap.put(str, "롯데카드"));

        String[] samsungCard = new String[]{
                "375987", "377989", "379183", "400912", "451245", "458532",
                "464942", "470587", "470588", "512360", "518831", "531070",
                "540447", "552070", "625817", "941009", "941029", "941034",
                "941085", "941088", "941090", "942090"
        };
        Arrays.stream(samsungCard)
              .forEach((str) -> cardCompanyMap.put(str, "삼성카드"));

        String[] shinhanCard = new String[]{
                "356296", "356297", "356900", "356901", "356902", "377981",
                "377982", "379983", "377988", "405753", "422155", "430972",
                "434975", "436420", "438676", "449914", "451842", "451845",
                "460561", "461954", "465887", "489023", "487033", "510737",
                "511187", "515594", "517134", "542158", "542879", "552576",
                "559410", "606045", "624348", "941061", "941083", "941098",
                "941161", "942061", "404678",
        };
        Arrays.stream(shinhanCard)
              .forEach((str) -> cardCompanyMap.put(str, "신한카드"));

        String[] wooriCard = new String[]{
                "490220", "447320", "4061", "4062", "4063", "4064",
                "4065", "421420", "515954", "535020", "537120", "537145",
                "538920", "548020", "552220", "657020", "942520", "356820",
                "538720", "944420", "944422", "944520",
        };
        Arrays.stream(wooriCard)
              .forEach((str) -> cardCompanyMap.put(str, "우리카드"));

        String[] hanaCard = new String[]{
                "4162", "440025", "356543", "356545", "371001", "371002",
                "371003", "371092", "374722", "374723", "374724", "377969",
                "379192", "379193", "402367", "408966", "411900", "411904",
                "411905", "418236", "455437", "457047", "459900", "459930",
                "459950", "4653", "4655", "493455", "516574", "518185",
                "523830", "524242", "524335", "524353", "531838", "532092",
                "5441", "546252", "553177", "502928", "636189", "6244",
                "626261", "941181"
        };
        Arrays.stream(hanaCard)
              .forEach((str) -> cardCompanyMap.put(str, "하나카드"));

        String[] hyundaiCard = new String[]{
                "3616", "401762", "401787", "402017", "402857", "4033",
                "418123", "433028", "451281", "524333", "5299", "543333",
                "552290", "552377", "559924", "949013", "949019", "949028",
                "949088", "949097",
        };
        Arrays.stream(hyundaiCard)
              .forEach((str) -> cardCompanyMap.put(str, "현대카드"));
    }

}
