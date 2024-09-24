package kr.co.olivepay.auth.global.utils;

import kr.co.olivepay.auth.entity.Tokens;
import kr.co.olivepay.auth.enums.Role;
import kr.co.olivepay.auth.global.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenGenerator {

    private final JwtConfig jwtConfig;

    public String generateToken(Map<String, Object> extraClaims,
                                 Long memberId, Role role, Duration expiredAt) {
        Date now = new Date();
        extraClaims.put("role", role.name());

        return Jwts.builder()
                   .issuer(jwtConfig.getIssuer())
                   .claims(extraClaims)
                   .subject(String.valueOf(memberId))
                   .issuedAt(now)
                   .expiration(new Date(now.getTime() + expiredAt.toMillis()))
                   .signWith(jwtConfig.getSecretKey())
                   .compact();
    }
}
