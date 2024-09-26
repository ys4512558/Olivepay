package kr.co.olivepay.gateway.global.util;

import kr.co.olivepay.gateway.global.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenUtils {

    private final JwtConfig jwtConfig;

    /**
     * 토큰 유효성 검사
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
        return token==null? null:
                Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    public String extractRole(String token) {
        return token==null? null:
                extractClaim(token, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                   .verifyWith(jwtConfig.getSecretKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }
}
