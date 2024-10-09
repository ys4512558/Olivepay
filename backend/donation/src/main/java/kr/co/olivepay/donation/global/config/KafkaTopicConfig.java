package kr.co.olivepay.donation.global.config;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.donation.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final KafkaProperties kafkaProperties;
    private final String MIN_INSYNC_REPLICAS = "min.insync.replicas";
    private final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    private final int PARTITIONS = 1;
    private final int REPLICAS = 2;


    @Bean
    public KafkaAdmin kafkaAdmin() {
        List<String> kafkaServers = kafkaProperties.getKAFKA_SERVERS();
        String bootstrapServer = String.join(",", kafkaServers);
        Map<String, Object> config = new HashMap<>();
        config.put(BOOTSTRAP_SERVERS, bootstrapServer);
        return new KafkaAdmin(config);
    }
    @Bean
    public NewTopic createCouponUsedSuccess() {
        return topicBuilder(Topic.COUPON_USED_FAIL);
    }
    @Bean
    public NewTopic createCouponUsedFail() {
        return topicBuilder(Topic.COUPON_USED_SUCCESS);
    }

    private NewTopic topicBuilder(String topic) {
        return TopicBuilder.name(topic)
                           .partitions(PARTITIONS)
                           .replicas(REPLICAS)
                           .configs(Collections.singletonMap(MIN_INSYNC_REPLICAS, "2"))
                           .build();
    }

}
