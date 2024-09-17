package kr.co.olivepay.card.service.init;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CardDreamTreeInitaliazer {
    public static Map<String, Boolean> isDefaultMap;

    @PostConstruct
    private void init() {
        isDefaultMap = new HashMap<>();
        for (int i = 1; i <= 500; i++) {
            Long cardNumber = 3562961000000000L + i;
            isDefaultMap.put(String.valueOf(cardNumber), true);
        }
    }
}
