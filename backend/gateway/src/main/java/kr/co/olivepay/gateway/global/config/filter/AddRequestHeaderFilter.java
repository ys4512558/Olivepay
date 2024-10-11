package kr.co.olivepay.gateway.global.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

@Slf4j
@Component
public class AddRequestHeaderFilter extends AbstractGatewayFilterFactory<AddRequestHeaderFilter.Config> {

    private final String MEMBER_ID = "memberId";
    private final String PATH = "path";
    private final String ROLE = "role";

    private final String USER = "USER";
    private final String NOT_USER = "NOT_USER";
    private final String HEADER_MEMBER_ID = "Member-Id";
    private final String HEADER_MEMBER_ROLE = "Member-Role";

    public AddRequestHeaderFilter() {
        super(Config.class);
    }

    /**
     * 커스텀헤더 필터
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // memberId 가져오기
            Long memberId = exchange.getAttribute(MEMBER_ID);
            if (memberId != null) {
                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                                           .header(HEADER_MEMBER_ID, String.valueOf(memberId))
                                                           .build();
                exchange = exchange.mutate().request(mutatedRequest).build();
            }

            // path & role 가져오기
            String path = exchange.getAttribute(PATH);
            if(path.matches(config.rolePath)){
                String role = USER.equals(exchange.getAttribute(ROLE))?
                        USER: NOT_USER;

                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                                           .header(HEADER_MEMBER_ROLE, role)
                                                           .build();
                exchange = exchange.mutate().request(mutatedRequest).build();
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // 필요한 설정값 추가
        String rolePath = "/api/franchises/[0-9]+";
    }
}