package kr.co.olivepay.gateway.global.config.filter;

import kr.co.olivepay.gateway.global.config.PathConfig;
import kr.co.olivepay.gateway.global.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WebSocketAuthenticationFilter extends AbstractGatewayFilterFactory<PathConfig> {

    private final String PREFIX = "Bearer ";
    private final int PREFIX_LENGTH = 7;
    private final TokenUtils tokenUtils;

    public WebSocketAuthenticationFilter(TokenUtils tokenUtils) {
        super(PathConfig.class);
        this.tokenUtils = tokenUtils;
    }
    @Override
    public GatewayFilter apply(PathConfig config) {
//      //TODO: 웹소켓 연결용 토큰이 개발되면 처리
        log.info("WebSocketAuthenticationFilter 필터 진입");
        return (exchange, chain) -> {
//            // URI에서 쿼리 파라미터를 Map으로 변환
//            String token = getToken(exchange);
//            if (token == null) {
//                String path = exchange.getRequest()
//                                      .getURI()
//                                      .getPath();
//                log.info("AuthenticationFilter: 토큰 없음, {}", path);
//                throw new AppException(TOKEN_INVALID);
//            }
//            log.info("AuthenticationFilter [Token] : {}", token);
//            //토큰 유효성 검사 (유효시간만 확인)
//            if (tokenUtils.validToken(token)) {
//
//            }
            return chain.filter(exchange);
        };
    }

    private static String getToken(ServerWebExchange exchange) {
        URI uri = exchange.getRequest()
                          .getURI();
        Map<String, List<String>> queryParams
                = UriComponentsBuilder.fromUri(uri)
                                      .build()
                                      .getQueryParams();
        List<String> tokens = queryParams.get("token");
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }
}
