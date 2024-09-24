package kr.co.olivepay.auth.service;

import kr.co.olivepay.auth.dto.req.LoginReq;
import kr.co.olivepay.auth.dto.req.RefreshReq;
import kr.co.olivepay.auth.dto.res.OwnerLoginRes;
import kr.co.olivepay.auth.dto.res.RefreshRes;
import kr.co.olivepay.auth.dto.res.UserLoginRes;
import kr.co.olivepay.auth.global.response.SuccessResponse;

public interface AuthService {

    SuccessResponse<UserLoginRes> userLogin(LoginReq loginReq);

    SuccessResponse<OwnerLoginRes> ownerLogin(LoginReq loginReq);

    SuccessResponse<RefreshRes> updateToken(RefreshReq refreshReq);
}
