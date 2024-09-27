package kr.co.olivepay.gateway.global.config;


import io.jsonwebtoken.security.Keys;
import kr.co.olivepay.gateway.global.enums.ErrorCode;
import kr.co.olivepay.gateway.global.handler.AppException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

import static kr.co.olivepay.gateway.global.enums.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
@Component
@Getter
@Setter
public class JwtConfig {

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.secretKey}")
    private String secretKey;

    public SecretKey getSecretKey() {
        try {
            String encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
            return Keys.hmacShaKeyFor(encoded.getBytes());
        } catch (Exception e){
            log.error("JWT 유효성 검증 중 오류 발생");
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }
}
