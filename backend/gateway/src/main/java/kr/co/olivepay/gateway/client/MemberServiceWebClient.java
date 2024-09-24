package kr.co.olivepay.gateway.client;

import kr.co.olivepay.gateway.dto.res.MemberRoleClientRes;
import kr.co.olivepay.gateway.dto.res.MemberRoleRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberServiceWebClient {

    private final WebClient.Builder webClientBuilder;
    @Value("${config.member.scheme}")
    private String memberScheme;
    @Value("${config.member.host}")
    private String memberHost;
    @Value("${config.member.path}")
    private String memberPath;
    @Value("${config.member.port}")
    private String memberPort;

    public Mono<MemberRoleRes> getMemberRole(Long memberId) {
        return webClientBuilder.build()
                               .get()
                               .uri(uriBuilder -> uriBuilder
                                       .scheme(memberScheme)
                                       .host(memberHost)
                                       .port(memberPort)
                                       .path(memberPath)
                                       .queryParam("memberId", memberId)
                                       .build())
                               .retrieve()
                               .bodyToMono(MemberRoleClientRes.class) // 새로운 DTO 클래스로 변환
                               .map(response -> response.data()); // data에서 MemberRole 객체 추출
    }
}