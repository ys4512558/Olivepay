package kr.co.olivepay.auth.service.impl;

import kr.co.olivepay.auth.dto.req.RefreshReq;
import kr.co.olivepay.auth.entity.Member;
import kr.co.olivepay.auth.entity.Tokens;
import kr.co.olivepay.auth.enums.Role;
import kr.co.olivepay.auth.global.handler.AppException;
import kr.co.olivepay.auth.global.utils.TokenGenerator;
import kr.co.olivepay.auth.repository.TokenRepository;
import kr.co.olivepay.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;

import static kr.co.olivepay.auth.global.enums.ErrorCode.TOKEN_INVALID;


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
     * 리프레쉬 토큰 유효성 확인 메소드<br>
     * redis에 토큰이 없거나, 일치하지 않는 경우 -> 입력받은 토큰은 유효하지 않음
     * @param refreshReq
     * @return
     */
    @Override
    public Long validateRefreshToken(RefreshReq refreshReq) {
        String accessToken = refreshReq.accessToken();
        String refreshToken = refreshReq.refreshToken();

        return  tokenRepository.findByRefreshToken(refreshToken)
                              .filter(storedToken -> storedToken.getAccessToken().equals(accessToken) &&
                                      storedToken.getRefreshToken().equals(refreshToken))
                              .map(Tokens::getMemberId)  // memberId 반환
                              .orElseThrow(() -> new AppException(TOKEN_INVALID));
    }

    /**
     * 해당 Member로 생성된 토큰 삭제
     * @param memberId
     */
    @Override
    public void deleteRefreshToken(Long memberId) {
        tokenRepository.findByMemberId(memberId)
                       .ifPresent(tokenRepository::delete);
    }
}
