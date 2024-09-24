package kr.co.olivepay.gateway.global.config.filter;

import kr.co.olivepay.gateway.client.MemberServiceWebClient;
import kr.co.olivepay.gateway.dto.res.MemberRoleRes;
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
import reactor.core.publisher.Mono;

import java.util.*;

import static kr.co.olivepay.gateway.global.enums.ErrorCode.*;

@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<PathConfig> {

    private final TokenUtils tokenUtils;
    private final MemberServiceWebClient memberServiceWebClient;

    public AuthorizationFilter(TokenUtils tokenUtils, MemberServiceWebClient memberServiceWebClient) {
        super(PathConfig.class);
        this.tokenUtils = tokenUtils;
        this.memberServiceWebClient = memberServiceWebClient;
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
            exchange.getAttributes().put("role", tokenRole);

            // 요청된 URL 가져오기
            String requestUrl = exchange.getRequest().getURI().getPath();

            // URL 권한 일치 여부 확인
            if (!isAuthorized(requestUrl, tokenRole, config)) {
                throw new AppException(ACCESS_DENIED);
            }

            // 요청된 accessToken의 권한이 memberId의 권한과 같은지 확인
            return validateTokenRole(tokenRole, memberId)
                    .doOnNext(memberRoleRes -> log.info("권한 검증 성공: {}", memberRoleRes))
                    .doOnError(e -> log.error("권한 검증 중 오류 발생: {}", e.getMessage()))
                    .then(Mono.fromRunnable(() -> {
                        // SecurityContext 권한 설정
                        setAuthentication(memberId, tokenRole);
                    }))
                    .then(chain.filter(exchange)); // 필터 체인 진행
        };
    }

    private Mono<MemberRoleRes> validateTokenRole(String tokenRole, Long memberId) {
        return memberServiceWebClient.getMemberRole(memberId)
                 .flatMap(memberRoleRes -> {
                     // 토큰에 맞는 회원이 없음
                     if (memberRoleRes.role() == null || memberRoleRes.role().isEmpty()) {
                         log.info("회원이 없습니다. 역할이 비어 있습니다.");
                         return Mono.error(new AppException(TOKEN_INVALID));
                     }

                     // 요청된 accessToken의 권한과 memberId의 권한을 비교
                     if (!tokenRole.equals(memberRoleRes.role())) {
                         log.info("권한 불일치: tokenRole = {}, memberRole = {}", tokenRole, memberRoleRes.role());
                         return Mono.error(new AppException(ACCESS_DENIED));
                     }

                     return Mono.just(memberRoleRes);
                 });
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