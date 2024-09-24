package kr.co.olivepay.auth.service.impl;

import kr.co.olivepay.auth.entity.Member;
import kr.co.olivepay.auth.entity.Tokens;
import kr.co.olivepay.auth.enums.Role;
import kr.co.olivepay.auth.global.utils.TokenGenerator;
import kr.co.olivepay.auth.repository.TokenRepository;
import kr.co.olivepay.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenGenerator tokenGenerator;

    private final TokenRepository tokenRepository;
    private final Duration ACCESS_TOKEN_EXPIRATION = Duration.ofDays(1);
    private final Duration REFRESH_TOKEN_EXPIRATION = Duration.ofDays(7);

    /**
     * 새로운 토큰 발급 및 Redis 저장
     * @param memberId 멤버 ID
     * @param role 권한
     * @return Tokens
     */
    @Override
    @Transactional
    public Tokens createTokens(Long memberId, Role role){
        // 중복 로그인 방지 - memberId로 token 찾아 제거
        deleteRefreshToken(memberId);

        // 토큰 생성
        String accessToken =
                tokenGenerator.generateToken(new HashMap<>(), memberId, role, ACCESS_TOKEN_EXPIRATION);
        String refreshToken =
                tokenGenerator.generateToken(new HashMap<>(), memberId, role, REFRESH_TOKEN_EXPIRATION);

        Tokens tokens = Tokens.builder()
                              .memberId(memberId)
                              .accessToken(accessToken)
                              .refreshToken(refreshToken)
                              .ttl(REFRESH_TOKEN_EXPIRATION.getSeconds())
                              .build();

        // 토큰 저장 및 반환
        return tokenRepository.save(tokens);
    }

    /**
     * RTR 방식을 위해 리프레시 토큰으로 액세스 토큰 발급 시 리프레시 토큰 갱신하여 저장 및 다시 반환
     * @param member
     * @return
     */
    @Override
    public String updateRefreshToken(Member member) {
        return "";
    }

    @Override
    public String getMemberIdByRefreshToken(String refreshToken) {
        return "";
    }

    @Override
    public void deleteRefreshToken(Long memberId) {
        tokenRepository.findByMemberId(memberId)
                       .ifPresent(tokenRepository::delete);
    }
}
