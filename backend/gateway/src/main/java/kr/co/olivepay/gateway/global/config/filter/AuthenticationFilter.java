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

    private final String ACCESS_TOKEN = "accessToken";
    private final String MEMBER_ID = "memberId";
    private final String PATH = "path";
    private final String ROLE = "role";
    private final String IS_SKIP = "isSkip";
    private final String PREFIX = "Bearer ";
    private final int PREFIX_LENGTH = 7;

    private final TokenUtils tokenUtils;
    private final TokenRepository tokenRepository;

    public AuthenticationFilter(TokenUtils tokenUtils, TokenRepository tokenRepository) {
        super(PathConfig.class);
        this.tokenUtils = tokenUtils;
        this.tokenRepository = tokenRepository;
    }

    /**
     * 인증 필터
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(PathConfig config) {
        return (exchange, chain) -> {
            // Authorization 추출
            String authorizationHeader =
                    exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // AccessToken, Role, MemberId, Url(path) 추출: null 가능
            String accessToken = getAccessToken(authorizationHeader);
            String tokenRole = tokenUtils.extractRole(accessToken);
            Long memberId = tokenUtils.extractMemberId(accessToken);
            String path = exchange.getRequest().getURI().getPath();

            // AccessToken, Role, MemberId, Url(path) 저장
            if(accessToken != null){
                exchange.getAttributes().put(ACCESS_TOKEN, accessToken);
                exchange.getAttributes().put(ROLE, tokenRole);
                exchange.getAttributes().put(MEMBER_ID, memberId);
            }
            exchange.getAttributes().put(PATH, path);

            // 인증이 필요없는 URL 체크
            if (isAuthenticationSkipped(path, config)) {
                exchange.getAttributes().put(IS_SKIP, true);
                return chain.filter(exchange);
            }

            // 허용된 URL이 아닌 경우 404 응답 처리
            if (!isAllowedPath(path, config)) {
                log.info("AuthenticationFilter: 허용되지 않은 경로 요청: {}", path);
                throw new AppException(NOT_FOUND);
            }

            // 토큰 유효성 검증
            if (accessToken == null) {
                log.info("AuthenticationFilter: 토큰 없음, {}", path);
                throw new AppException(TOKEN_INVALID);
            }
            if(!tokenUtils.validToken(accessToken)){
                log.info("AuthenticationFilter: 토큰 유효성 검증 실패: {}, {}", path, accessToken);
                throw new AppException(TOKEN_INVALID);
            }

            // redis에서 Tokens 추출
            Tokens tokens = tokenRepository.findById(memberId)
                                           .orElseThrow(() -> new AppException(TOKEN_INVALID));

            // 요청된 accessToken이 Redis에 저장된 값과 같은지 확인
            if (!tokens.getAccessToken().equals(accessToken)) {
                log.info("AuthenticationFilter: 토큰 권한 검증 실패: {}", path);
                throw new AppException(TOKEN_INVALID);
            }

            // 유효하므로 필터 체인 연결
            return chain.filter(exchange);
        };
    }

    /**
     * 헤더의 Authorization에서 AccessToken을 추출하는 메소드
     * @param authorizationHeader
     * @return AccessToken 또는 null
     */
    private String getAccessToken(String authorizationHeader) {
        return Optional.ofNullable(authorizationHeader)
                       .filter(header -> header.startsWith(PREFIX))
                       .map(header -> header.substring(PREFIX_LENGTH))
                       .orElse(null);
    }


    /**
     * 인증 없이 접근할 수 있는 Request Url 여부 판단 메소드
     * @param path
     * @param config
     * @return boolean
     */
    private boolean isAuthenticationSkipped(String path, PathConfig config) {
        return config.getExcludedPathPrefixes().stream().anyMatch(path::startsWith) ||
                config.getExcludedExactPaths().contains(path) ||
                config.getExcludedExactPathPatterns().stream().anyMatch(path::matches);
    }

    /**
     * 허용된 경로 리스트와 정규 표현식 패턴을 결합하여 검사
     * @param requestUrl
     * @param config
     * @return boolean
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