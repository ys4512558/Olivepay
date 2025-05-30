package kr.co.olivepay.card.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "config.kafka")
public class KafkaProperties {

    public static final String KAFKA_GROUP_ID_CONFIG = "payment-orchestrator";
    @Getter
    @Setter
    private List<String> KAFKA_SERVERS;

}
