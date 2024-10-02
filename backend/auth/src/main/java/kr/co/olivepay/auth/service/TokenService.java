package kr.co.olivepay.auth.service;

import kr.co.olivepay.auth.dto.req.RefreshReq;
import kr.co.olivepay.auth.entity.Tokens;
import kr.co.olivepay.auth.enums.Role;

public interface TokenService {

    Tokens createTokens(Long memberId, Role role);

    Long validateRefreshToken(RefreshReq RefreshReq);

    void deleteToken(Long memberId);
}
