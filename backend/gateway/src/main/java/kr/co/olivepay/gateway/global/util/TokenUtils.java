package kr.co.olivepay.gateway.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import kr.co.olivepay.gateway.global.config.JwtConfig;
import kr.co.olivepay.gateway.global.handler.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static kr.co.olivepay.gateway.global.enums.ErrorCode.TOKEN_INVALID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenUtils {

    private final JwtConfig jwtConfig;

    /**
     * 토큰 유효성 검사
     *
     * @param token
     * @return
     */
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long extractMemberId(String token) {
        return token == null ? null :
                Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    public String extractRole(String token) {
        return token == null ? null :
                extractClaim(token, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * 현재 시간 기준 토큰이 만료되었는지 확인
     *
     * @param token
     * @return 만료 ? true : false
     */
    public boolean isTokenExpired(String token) {
        try {
            Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return false;
        } catch (Exception e) {
            log.error("결제 토큰 만료");
            return true;
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                       .verifyWith(jwtConfig.getSecretKey())
                       .build()
                       .parseSignedClaims(token)
                       .getPayload();
        } catch (Exception e) {
            log.error("JWT 토큰 만료");
            throw new AppException(TOKEN_INVALID);
        }
    }
}
