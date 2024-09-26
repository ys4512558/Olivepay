package kr.co.olivepay.gateway.global.config.filter;

import kr.co.olivepay.gateway.entity.Tokens;
import kr.co.olivepay.gateway.global.config.PathConfig;
import kr.co.olivepay.gateway.global.handler.AppException;
import kr.co.olivepay.gateway.global.util.TokenUtils;
import kr.co.olivepay.gateway.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

import static kr.co.olivepay.gateway.global.enums.ErrorCode.NOT_FOUND;
import static kr.co.olivepay.gateway.global.enums.ErrorCode.TOKEN_INVALID;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<PathConfig> {

    private final TokenUtils tokenUtils;
    private final TokenRepository tokenRepository;

    public AuthenticationFilter(TokenUtils tokenUtils, TokenRepository tokenRepository) {
        super(PathConfig.class);
        this.tokenUtils = tokenUtils;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public GatewayFilter apply(PathConfig config) {
        return (exchange, chain) -> {
            // Authorization 추출
            String authorizationHeader =
                    exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            String accessToken = getAccessToken(authorizationHeader);
            String path = exchange.getRequest().getURI().getPath();
            exchange.getAttributes().put("path", path);

            // URL 필터링 체크
            if (isAuthenticationSkipped(path, config)) {
                return chain.filter(exchange);
            }

            // 허용된 URL이 아닌 경우 404 응답 처리
            if (!isAllowedPath(path, config)) {
                log.info("AuthenticationFilter: 허용되지 않은 경로 요청: {}", path);
                throw new AppException(NOT_FOUND);
            }

            // 토큰 유효성 검증
            if (accessToken == null || !tokenUtils.validToken(accessToken)) {
                log.info("AuthenticationFilter: 토큰 유효성 검증 실패: {}", path);
                throw new AppException(TOKEN_INVALID);
            }

            // 토큰에서 memberId 추출
            Long memberId = tokenUtils.extractMemberId(accessToken);

            // redis에서 Tokens 추출
            Tokens tokens = tokenRepository.findById(memberId)
                                           .orElseThrow(() -> new AppException(TOKEN_INVALID));

            // 요청된 accessToken이 Redis에 저장된 값과 같은지 확인
            if (!tokens.getAccessToken().equals(accessToken)) {
                log.info("AuthenticationFilter: 토큰 권한 검증 실패: {}", path);
                throw new AppException(TOKEN_INVALID);
            }

            // 유효하므로 필터 체인 연결
            // accessToken을 exchange에 저장
            exchange.getAttributes().put("accessToken", accessToken);
            exchange.getAttributes().put("memberId", memberId);
            return chain.filter(exchange);
        };
    }

    private String getAccessToken(String authorizationHeader) {
        return Optional.ofNullable(authorizationHeader)
                       .filter(header -> header.startsWith("Bearer "))
                       .map(header -> header.substring(7))
                       .orElse(null);
    }


    private boolean isAuthenticationSkipped(String path, PathConfig config) {
        return config.getExcludedPathPrefixes().stream().anyMatch(path::startsWith) ||
                config.getExcludedExactPaths().contains(path) ||
                config.getExcludedExactPathPatterns().stream().anyMatch(path::matches);
    }

    /**
     * 허용된 경로 리스트와 정규 표현식 패턴을 결합하여 검사
     * @param requestUrl
     * @param config
     * @return
     */
    private boolean isAllowedPath(String requestUrl, PathConfig config) {
        return config.getRoleUrlMappingsExact().values().stream()
                     .flatMap(Set::stream)
                     .anyMatch(requestUrl::equals) ||
                config.getRoleUrlMappingsMatches().values().stream()
                      .flatMap(Set::stream)
                      .anyMatch(requestUrl::matches);
    }
}