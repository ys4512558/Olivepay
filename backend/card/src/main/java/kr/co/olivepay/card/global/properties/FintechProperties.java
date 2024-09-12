package kr.co.olivepay.card.global.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "config.fintech")
public class FintechProperties {

    private String apiKey;
    private String institutionCode;
    private String fintechAppNo;
    private String accountTypeUniqueNo;
    private String cardUniqueNo;
    private String fintechURI;

}
