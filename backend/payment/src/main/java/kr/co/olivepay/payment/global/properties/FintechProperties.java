package kr.co.olivepay.payment.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "config.fintech")
public class FintechProperties {

    private String apiKey;
    private String institutionCode;
    private String fintechAppNo;
    private String accountTypeUniqueNo;
    private String fintechURI;

}
