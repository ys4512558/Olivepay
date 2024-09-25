package kr.co.olivepay.auth.service;

import kr.co.olivepay.auth.entity.Member;
import kr.co.olivepay.auth.entity.Tokens;
import kr.co.olivepay.auth.enums.Role;

public interface TokenService {

    Tokens createTokens(Long memberId, Role role);

    String updateRefreshToken(Member member);

    String getMemberIdByRefreshToken(String refreshToken);

    void deleteRefreshToken(Long memberId);
}
