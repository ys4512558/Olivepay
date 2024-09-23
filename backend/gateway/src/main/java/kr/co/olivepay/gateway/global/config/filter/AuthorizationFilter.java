package kr.co.olivepay.gateway.global.config.filter;

import kr.co.olivepay.gateway.global.config.PathConfig;
import kr.co.olivepay.gateway.global.handler.AppException;
import kr.co.olivepay.gateway.global.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

import static kr.co.olivepay.gateway.global.enums.ErrorCode.ACCESS_DENIED;

@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<PathConfig> {

    private final TokenUtils tokenUtils;

    public AuthorizationFilter(TokenUtils tokenUtils) {
        super(PathConfig.class);
        this.tokenUtils = tokenUtils;
    }

    @Override
    public GatewayFilter apply(PathConfig config) {
        return (exchange, chain) -> {
            String accessToken = exchange.getAttribute("accessToken");
            Long memberId = exchange.getAttribute("memberId");

            // accessToken이 없으면 다음 필터로 넘어감
            if(accessToken == null){
                return chain.filter(exchange);
            }

            // accessToken에서 권한 추출
            String tokenRole = tokenUtils.extractRole(accessToken);

            // TODO: 요청된 accessToken의 권한이 memberId의 권한과 같은지 확인


            // 요청된 URL 가져오기
            String requestUrl = exchange.getRequest().getURI().getPath();

            // URL 권한 일치 여부 확인
            if (!isAuthorized(requestUrl, tokenRole, config)) {
                throw new AppException(ACCESS_DENIED);
            }

            // SecurityContext 권한 설정
            setAuthentication(memberId, tokenRole);
            // 체인
            return chain.filter(exchange);
        };
    }
    /**
     * 허용된 경로 리스트와 정규 표현식 패턴을 결합하여 검사
     * @param requestUrl
     * @param role
     * @param config
     * @return
     */
    private boolean isAuthorized(String requestUrl, String role, PathConfig config) {
        return config.getRoleUrlMappingsExact()
                     .getOrDefault(role, Collections.emptySet())
                     .stream()
                     .anyMatch(requestUrl::startsWith) ||
                config.getRoleUrlMappingsMatches()
                      .getOrDefault(role, Collections.emptySet())
                      .stream()
                      .anyMatch(requestUrl::matches);
    }

    private void setAuthentication(Long memberId, String role) {
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(role));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberId, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}