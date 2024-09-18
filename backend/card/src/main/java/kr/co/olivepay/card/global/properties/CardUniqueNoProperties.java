package kr.co.olivepay.card.global.properties;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "config.fintech.card")
public class CardUniqueNoProperties {

    private Map<String, String> products;

    public String getCardProduct(String cardCompanyName) {
        String simpleCardCompanyName = null;
        switch (cardCompanyName) {
            case "KB국민카드":
                simpleCardCompanyName = "KB";
                break;
            case "삼성카드":
                simpleCardCompanyName = "SS";
                break;
            case "롯데카드":
                simpleCardCompanyName = "LT";
                break;
            case "우리카드":
                simpleCardCompanyName = "WR";
                break;
            case "신한카드":
                simpleCardCompanyName = "SH";
                break;
            case "꿈나무카드":
                simpleCardCompanyName = "DT";
                break;
            case "현대카드":
                simpleCardCompanyName = "HD";
                break;
            case "BC카드":
                simpleCardCompanyName = "BC";
                break;
            case "NH농협카드":
                simpleCardCompanyName = "NH";
                break;
            case "하나카드":
                simpleCardCompanyName = "HN";
                break;
            case "IBK기업은행카드":
                simpleCardCompanyName = "IBK";
                break;
        }
        return products.get(simpleCardCompanyName);
    }
}