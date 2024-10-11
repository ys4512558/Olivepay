package kr.co.olivepay.gateway.global.config;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class PathConfig {
    // startsWith로 처리할 URL 패턴들
    private List<String> excludedPathPrefixes = List.of(
            // Swagger 관련
            "/api/member-swagger",
            "/api/common-swagger",
            "/api/auth-swagger",
            "/api/card-swagger",
            "/api/donation-swagger",
            "/api/franchise-swagger",
            "/api/payment-swagger",
            "/api/funding-swagger",
            "/api-docs",
            "/swagger-ui.html",
            "/swagger-ui",
            "/docs", "/webjars", "/v3",
            // 정적 리소스
            "/js",
            "/css",
            "/images",
            "/commons/file"
    );

    // 인증 스킵 URL
    // 정확히 매칭될 URL들 (equals 비교)
    private Set<String> excludedExactPaths = Set.of(
            "/api/members/duplicates/phone",
            "/api/members/users/sign-up",
            "/api/members/owners/sign-up",
            "/api/auths/users/login",
            "/api/auths/owners/login",
            "/api/auths/refresh",
            "/api/commons/sms",
            "/api/commons/sms/check",
            "/api/donations",
            "/api/donations/donors",
            "/api/donations/donors/my",
            "/api/franchises",
            "/api/fundings/total",
            "/api/fundings/usage",
            "/actuator"
    );

    // 인증 스킵 URL
    // 정확히 매칭될 URL들 (matches로 비교)
    private Set<String> excludedExactPathPatterns = Set.of(
            "/api/franchises/[0-9]+",
            "/api/franchises/likes/[0-9]+",
            "/api/franchises/reviews/[0-9]+"
    );

    // 역할에 따른 URL 매핑 (equals 비교)
    private Map<String, Set<String>> roleUrlMappingsExact = Map.of(
            // TEMP_USER가 접근할 수 있는 URL
            "TEMP_USER", Set.of(
                    "/api/commons/ocr",
                    "/api/auths/logout",
                    "/api/cards"
            ),
            // USER가 접근할 수 있는 URL
            "USER", Set.of(
                    "/api/commons/ocr",
                    "/api/members/users/password-check",
                    "/api/members/users/password-change",
                    "/api/members/users/pin",
                    "/api/members/users",
                    "/api/auths/logout",
                    "/api/auths/users/payment-token",
                    "/api/donations/coupons/my",
                    "/api/cards",
                    "/api/payments/pay",
                    "/api/payments/history/user",
                    "/api/franchises/likes/user",
                    "/api/franchises/reviews/user",
                    "/api/franchises/reviews/available"
            ),
            // OWNER가 접근할 수 있는 URL
            "OWNER", Set.of(
                    "/api/auths/logout",
                    "/api/franchises/qr"
            )
    );

    // 역할에 따른 URL 매핑 (matches로 비교)
    private Map<String, Set<String>> roleUrlMappingsMatches = Map.of(
            // TEMP_USER가 접근할 수 있는 URL 패턴
            "TEMP_USER", Set.of(
                    "/api/cards/[0-9]+"
            ),
            // USER가 접근할 수 있는 URL 패턴
            "USER", Set.of(
                    "/api/donations/coupons/[0-9]+",
                    "/api/cards/[0-9]+",
                    "/api/franchises/likes/user/[0-9]+",
                    "/api/franchises/reviews/user/[0-9]+"
            ),
            // OWNER가 접근할 수 있는 URL 패턴
            "OWNER", Set.of(
                    "/api/donations/coupons/[0-9]+",
                    "/api/payments/history/[0-9]+"
            )
    );
}
