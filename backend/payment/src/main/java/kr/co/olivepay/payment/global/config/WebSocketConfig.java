package kr.co.olivepay.payment.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${config.socket.END_POINT}")
    private String END_POINT;
    @Value("${config.socket.TOPIC_PREFIX}")
    private String TOPIC_PREFIX;

    /**
     * 웹소켓 연결을 위한 엔드포인트 설정
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(END_POINT)
                //TODO: 프론트엔트 CORS 도메인 설정 (게이트웨이에 위임 가능하다면 설정 X)
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * 클라이언트가 구독할 URL의 prefix를 설정
     * 클라이언트 -> 서버로 메시지를 보낼 필요가 없기 때문에
     * 이를 처리하는 세팅은 따로 하지 않음.
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(TOPIC_PREFIX);
    }
}
