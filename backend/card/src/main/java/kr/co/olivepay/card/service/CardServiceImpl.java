package kr.co.olivepay.card.service;

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
import kr.co.olivepay.card.openapi.dto.res.account.AccountDepositRec;
import kr.co.olivepay.card.openapi.dto.res.account.AccountRec;
import kr.co.olivepay.card.openapi.dto.res.card.CardRec;
import kr.co.olivepay.card.openapi.service.FintechService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static kr.co.olivepay.card.global.enums.ErrorCode.*;
import static kr.co.olivepay.card.service.init.CardCompanyPrefixInitializer.cardCompanyMap;
import static kr.co.olivepay.card.service.init.CardDreamTreeInitaliazer.isDefaultMap;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final FintechService fintechService;
    private final CardTransactionService cardTransactionService;
    private final AccountMapper accountMapper;
    private final CardMapper cardMapper;
    private final String DREAM_TREE_CARD = "꿈나무카드";
    private final Long INIT_BALANCE = 3000000000L;
    private final String INIT_DEPOSIT_SUMMARY = "초기 계좌 입금";

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

        //꿈나무 카드 여부
        Boolean isDefault = isDefaultMap.getOrDefault(cardRegisterReq.realCardNumber(), false);
        //꿈나무 카드일 때 이미 등록했는지 확인
        if (isDefault) {
            checkDefaultCardDuplicate(memberId);
        }

        // 카드 앞 6자리 슬라이스
        String prefix6 = realCardNumber.substring(0, 6);
        // 카드 앞 4자리 슬라이스
        String prefix4 = realCardNumber.substring(0, 4);

        // 6자리 비교 -> 4자리 비교 -> 없으면 예외
        String cardCompanyName
                = cardCompanyMap.getOrDefault(prefix6, cardCompanyMap.getOrDefault(prefix4, null));
        cardCompanyName = isDefault ? DREAM_TREE_CARD : cardCompanyName;

        if (cardCompanyName == null) {
            throw new AppException(CARDCOMPANY_NOT_EXIST);
        }

        //계좌 생성
        AccountRec accountRec = fintechService.createAccount(userKey);
        AccountDepositRec accountDepositRec = fintechService.depositAccount(
                userKey, accountRec.getAccountNo(), String.valueOf(INIT_BALANCE), INIT_DEPOSIT_SUMMARY
        );

        //카드 생성
        CardRec cardRec = fintechService.createCard(userKey, accountRec.getAccountNo(), cardCompanyName);


        Account account = accountMapper.toEntity(accountRec);
        // 이름으로 카드사 객체 가져오기
        CardCompany cardCompany = cardTransactionService.getCardCompany(cardCompanyName);
        Card card = cardMapper.toEntity(memberId, account, cardRec, cardRegisterReq, cardCompany);

        //DB에 등록
        return cardTransactionService.registerCard(account, card);
    }

    /**
     * 꿈나무 카드 중복 등록 확인
     *
     * @param memberId
     */
    private void checkDefaultCardDuplicate(final Long memberId) {
        Optional<Card> defaultCard = cardTransactionService.getDefaultCard(memberId);
        if (defaultCard.isPresent()) {
            throw new AppException(DEFAULT_CARD_DUPLICATE);
        }
    }

    /**
     * 실제 카드 등록 시 중복되는지 확인하는 메서드
     *
     * @param realCardNumber
     */
    private void checkDuplicateCardNumber(final String realCardNumber) {
        Optional<Card> optionalCard = cardTransactionService.getCard(realCardNumber);

        if (optionalCard.isPresent()) {
            throw new AppException(CARD_DUPLICATE);
        }
    }

    /**
     * 카드 삭제 메서드
     *
     * @param memberId
     * @param cardId
     */
    @Override
    public void deleteCard(final Long memberId, final Long cardId) {
        cardTransactionService.deleteCard(memberId, cardId);
    }

    /**
     * 내 카드 목록 조회
     *
     * @param memberId
     * @return 내가 등록한 카드 리스트
     */
    @Override
    public List<MyCardSearchRes> getMyCardList(final Long memberId) {
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
    public List<TransactionCardSearchRes> getTransactionCardList(final Long memberId, CardSearchReq cardSearchReq) {
        List<Card> cardList = cardTransactionService.getTransactionCardList(memberId, cardSearchReq);
        return cardList.stream()
                       .map(cardMapper::toTransactionCardSearchRes)
                       .toList();
    }
}
