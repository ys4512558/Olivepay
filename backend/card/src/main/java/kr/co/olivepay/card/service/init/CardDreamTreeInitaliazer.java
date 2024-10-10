package kr.co.olivepay.card.service.init;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CardDreamTreeInitaliazer {
    public static Map<String, Boolean> isDefaultMap;
    private final Long ACCOUNT_COUNT = 300_000L;
    private final Long DEFAULT_CARD_PREFIX = 3562961000000000L;

    @PostConstruct
    private void init() {
        isDefaultMap = new HashMap<>();
        for (int i = 1; i <= ACCOUNT_COUNT; i++) {
            Long cardNumber = DEFAULT_CARD_PREFIX + i;
            isDefaultMap.put(String.valueOf(cardNumber), true);
        }
    }
}
