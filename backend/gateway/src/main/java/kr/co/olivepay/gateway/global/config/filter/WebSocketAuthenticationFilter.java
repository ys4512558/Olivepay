package kr.co.olivepay.gateway.global.config.filter;

import kr.co.olivepay.gateway.global.config.PathConfig;
import kr.co.olivepay.gateway.global.handler.AppException;
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

import static kr.co.olivepay.gateway.global.enums.ErrorCode.*;

@Slf4j
@Component
public class WebSocketAuthenticationFilter extends AbstractGatewayFilterFactory<PathConfig> {

    private final TokenUtils tokenUtils;

    public WebSocketAuthenticationFilter(TokenUtils tokenUtils) {
        super(PathConfig.class);
        this.tokenUtils = tokenUtils;
    }
    @Override
    public GatewayFilter apply(PathConfig config) {
        log.info("WebSocketAuthenticationFilter 필터 진입");
        return (exchange, chain) -> {
            String token = getToken(exchange);
            log.info("WebSocketAuthenticationFilter: 토큰 [{}]", token);
            if (token == null) {
                String path = exchange.getRequest()
                                      .getURI()
                                      .getPath();
                log.info("WebSocketAuthenticationFilter: 토큰 없음, {}", path);
                throw new AppException(PAYMENT_TOKEN_NOT_FOUND);
            }
            log.info("WebSocketAuthenticationFilter [Token] : {}", token);
            //토큰 유효성 검사 (유효성, 유효시간)
            if (!validToken(token)) {
                log.info("WebSocketAuthenticationFilter: 토큰이 만료됨 : [{}]", token);
                throw new AppException(PAYMENT_TOKEN_INVALID);
            }
            //Role이 USER가 아니면
            if (!getRole(token).equals("USER")) {
                log.info("허용되지 않은 경로");
                throw new AppException(BAD_REQUEST);
            }
            return chain.filter(exchange);
        };
    }

    private boolean validToken(String token) {
        if (!tokenUtils.validToken(token) || tokenUtils.isTokenExpired(token)) {
            return false;
        }
        return true;
    }

    private static String getToken(ServerWebExchange exchange) {
        URI uri = exchange.getRequest()
                          .getURI();
        Map<String, List<String>> queryParams
                = UriComponentsBuilder.fromUri(uri)
                                      .build()
                                      .getQueryParams();
        log.info("queryParams : [{}]", queryParams);
        List<String> tokens = queryParams.getOrDefault("paymentToken", null);
        return tokens != null ? tokens.get(0) : null;
    }

    private String getRole(String token) {
        return tokenUtils.extractRole(token);
    }
}
