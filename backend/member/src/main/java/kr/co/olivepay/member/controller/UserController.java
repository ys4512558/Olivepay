package kr.co.olivepay.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.dto.res.UserKeyRes;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kr.co.olivepay.member.global.enums.SuccessCode.GET_USER_KEY_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/users")
public class UserController {

    @Value("${config.fintech.userKeyDummy}")
    private String userKey;
    private final UserService userService;

    @PostMapping("/sign-up")
    @Operation(description = "유저 정보를 받아 회원가입 합니다.", summary = "유저 회원가입")
    public ResponseEntity<Response<NoneResponse>> registerUser(
            @RequestBody @Valid UserRegisterReq request)
    {
        SuccessResponse<NoneResponse> response = userService.registerUser(request);
        return Response.success(response);
    }

    @GetMapping("/user-key")
    @Operation(description = "금유망 API의 UserKey를 반환합니다.", summary = "금융망 API UserKey 조회 - 더미")
    public ResponseEntity<Response<UserKeyRes>> getUserKey()
    {
        UserKeyRes userKeyRes = UserKeyRes.builder()
                                          .userKey(userKey)
                                          .build();
        SuccessResponse<UserKeyRes> response = new SuccessResponse<>(GET_USER_KEY_SUCCESS, userKeyRes);

        return Response.success(response);
    }
}
