package kr.co.olivepay.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.co.olivepay.auth.dto.req.LoginReq;
import kr.co.olivepay.auth.dto.req.RefreshReq;
import kr.co.olivepay.auth.dto.res.OwnerLoginRes;
import kr.co.olivepay.auth.dto.res.RefreshRes;
import kr.co.olivepay.auth.dto.res.UserLoginRes;
import kr.co.olivepay.auth.global.enums.NoneResponse;
import kr.co.olivepay.auth.global.response.Response;
import kr.co.olivepay.auth.global.response.SuccessResponse;
import kr.co.olivepay.auth.service.AuthService;
import kr.co.olivepay.core.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auths")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users/login")
    @Operation(summary = "유저 로그인", description = "전화번호와 비밀번호를 통해 로그인 합니다.")
    public ResponseEntity<Response<UserLoginRes>> userLogin(
            @RequestBody @Valid LoginReq loginReq
    ) {
        SuccessResponse<UserLoginRes> response = authService.userLogin(loginReq);

        return Response.success(response);
    }

    @PostMapping("/owners/login")
    @Operation(summary = "가맹점주 로그인", description = "전화번호와 비밀번호를 통해 로그인 합니다.")
    public ResponseEntity<Response<OwnerLoginRes>> ownerLogin(
            @RequestBody @Valid LoginReq loginReq
    ) {
        SuccessResponse<OwnerLoginRes> response = authService.ownerLogin(loginReq);

        return Response.success(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "리프레시 토큰 재발급", description = "리프레시 토큰을 통해 새로운 토큰을 발급합니다.")
    public ResponseEntity<Response<RefreshRes>> updateToken(
            @RequestBody @Valid RefreshReq refreshReq
    ) {
        SuccessResponse<RefreshRes> response = authService.updateToken(refreshReq);

        return Response.success(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "토큰을 통해 로그아웃합니다.")
    public ResponseEntity<Response<NoneResponse>> logout(
            @RequestHeader HttpHeaders headers
    ){
        Long memberId = CommonUtil.getMemberId(headers);
        SuccessResponse<NoneResponse> response = authService.logout(memberId);

        return Response.success(response);
    }
}
