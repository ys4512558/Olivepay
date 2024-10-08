package kr.co.olivepay.card.transaction.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.card.dto.res.AccountBalanceCheckRes;
import kr.co.olivepay.card.global.properties.KafkaProperties;
import kr.co.olivepay.card.service.CardEventService;
import kr.co.olivepay.card.transaction.publisher.TransactionEventPublisher;
import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceCheckEvent;
import kr.co.olivepay.core.transaction.topic.event.account.AccountBalanceDetailCheckEvent;
import kr.co.olivepay.core.transaction.topic.event.account.result.AccountBalanceCheckFailEvent;
import kr.co.olivepay.core.transaction.topic.event.account.result.AccountBalanceCheckSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountBalanceCheckEventListener implements KafkaEventListener {

    private final ObjectMapper objectMapper;
    private final CardEventService cardEventService;
    private final TransactionEventPublisher eventPublisher;
    private final String WRONG_REQUEST = "잘못된 요청입니다.";

    /**
     * 계좌 잔액 체크 이벤트 리스너
     * 1. 해당 결제에 사용되는 모든 카드에 대해 잔액 체크
     * - 성공 : 계좌 잔액 체크 성공 이벤트 발행
     * - 실패 : 계좌 잔액 체크 실패 이벤트 발행
     * 1. 예외 발생 : (잘못된 요청입니다.)
     * 2. 잔액 부족 : cardId : [cardId], cardNumber : [realCardNumber] : 잔액 부족"
     *
     * @param record
     */
    @Override
    @KafkaListener(topics = Topic.ACCOUNT_BALANCE_CHECK, groupId = KafkaProperties.KAFKA_GROUP_ID_CONFIG)
    public void onMessage(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        log.info("잔액 체크 프로세스 시작");
        log.info("key : [{}], state : [{}]", key, "ACCOUNT_BALANCE_CHECK");
        try {
            AccountBalanceCheckEvent accountBalanceCheckEvent
                    = objectMapper.readValue(value, AccountBalanceCheckEvent.class);
            log.info("AccountBalanceCheckEvent : {}", accountBalanceCheckEvent);
            //유저의 핀테크 API 키
            String userKey = accountBalanceCheckEvent.userKey();
            //결제에 사용되는 카드 3개와 연결된 계좌 잔액 체크
            List<AccountBalanceDetailCheckEvent> accountBalanceDetailCheckEventList
                    = accountBalanceCheckEvent.accountBalanceDetailCheckEventList();

            for (AccountBalanceDetailCheckEvent accountBalanceDetailCheckEvent : accountBalanceDetailCheckEventList) {
                Long cardId = accountBalanceDetailCheckEvent.cardId();
                Long price = accountBalanceDetailCheckEvent.price();

                //계좌 잔액 체크
                AccountBalanceCheckRes check = cardEventService.checkAccountBalance(userKey, cardId, price);
                //계좌 잔액 부족 시 실패 이벤트 발행
                if (!check.isValid()) {
                    publishAccountBalanceCheckFailEvent(key, check.failReason());
                    break;
                }
            }
            //모든 계좌 잔액 체크 완료 시 성공 이벤트 발행
            publishAccountBalanceCheckSuccessEvent(key);
        } catch (Exception e) {
            publishAccountBalanceCheckFailEvent(key, WRONG_REQUEST);
        }
        log.info("잔액 체크 프로세스 종료");
    }

    /**
     * 계좌 잔액 체크 성공 이벤트 발행
     *
     * @param key
     */
    private void publishAccountBalanceCheckSuccessEvent(String key) {
        eventPublisher.publishEvent(
                Topic.ACCOUNT_BALANCE_CHECK_SUCCESS,
                key,
                new AccountBalanceCheckSuccessEvent()
        );
    }

    /**
     * 계좌 잔액 체크 실패 이벤트 발행
     *
     * @param key
     * @param failReason
     */
    private void publishAccountBalanceCheckFailEvent(String key, String failReason) {
        AccountBalanceCheckFailEvent accountBalanceCheckFailEvent
                = AccountBalanceCheckFailEvent.builder()
                                              .failReason(failReason)
                                              .build();
        eventPublisher.publishEvent(
                Topic.ACCOUNT_BALANCE_CHECK_FAIL,
                key,
                accountBalanceCheckFailEvent
        );
    }
}
