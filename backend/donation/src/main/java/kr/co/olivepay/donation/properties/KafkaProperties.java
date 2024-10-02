package kr.co.olivepay.donation.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config.kafka")
public class KafkaProperties {

    public static final String KAFKA_GROUP_ID_CONFIG = "payment-orchestrator";
    @Getter
    @Setter
    private String KAFKA_SERVER;
    @Getter
    @Setter
    private String KAFKA_PORT;

}
