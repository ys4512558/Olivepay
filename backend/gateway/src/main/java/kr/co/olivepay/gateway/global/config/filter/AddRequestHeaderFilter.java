package kr.co.olivepay.gateway.global.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

@Slf4j
@Component
public class AddRequestHeaderFilter extends AbstractGatewayFilterFactory<AddRequestHeaderFilter.Config> {

    public AddRequestHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // memberId 가져오기
            String memberId = exchange.getAttribute("memberId");

            if (memberId != null) {
                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                                           .header("Member-Id", memberId)
                                                           .build();
                exchange = exchange.mutate().request(mutatedRequest).build();
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // 필요한 설정값 추가
    }
}