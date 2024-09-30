package kr.co.olivepay.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import kr.co.olivepay.core.member.dto.res.MemberRoleRes;
import kr.co.olivepay.core.member.dto.res.UserKeyRes;
import kr.co.olivepay.core.util.CommonUtil;
import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.global.enums.ErrorCode;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.handler.AppException;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.global.utils.HeaderUtil;
import kr.co.olivepay.member.service.MemberService;
import kr.co.olivepay.member.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kr.co.olivepay.member.global.enums.ErrorCode.INTERNAL_SERVER_ERROR;
import static kr.co.olivepay.member.global.enums.SuccessCode.GET_USER_KEY_SUCCESS;
import static kr.co.olivepay.member.global.enums.SuccessCode.USER_PROMOTE_SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members/users")
public class UserController {

    private final UserService userService;
    private final MemberService memberService;

    @PostMapping("/sign-up")
    @Operation(
            description = "유저 정보를 받아 회원가입 합니다.",
            summary = "유저 회원가입",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "회원(유저) 정보",
                    required = true,
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value = """
                                           {
                                                "name": "홍길동",
                                                "password": "Password123!",
                                                "phoneNumber": "01011112222",
                                                "nickname": "홍길동의 별명",
                                                "birthdate": "20080808",
                                                "pin": "001122"
                                            }
                                            """,
                                            description = "회원(유저) 정보 예제"
                                    )
                            }
                    )
            )
    )
    public ResponseEntity<Response<NoneResponse>> registerUser(
            @RequestBody @Valid UserRegisterReq request)
    {
        SuccessResponse<NoneResponse> response = userService.registerUser(request);
        return Response.success(response);
    }

    @GetMapping("/user-key")
    @Operation(description = "금융망 API의 UserKey를 반환합니다.", summary = "금융망 API UserKey 조회")
    public ResponseEntity<Response<UserKeyRes>> getUserKey(@RequestHeader HttpHeaders headers)
    {
        Long memberId = HeaderUtil.getMemberId(headers);

        SuccessResponse<UserKeyRes> response = userService.getUserKey(memberId);
        return Response.success(response);
    }

    @PostMapping("/promote")
    @Operation(description = "임시 회원을 일반 회원으로 권한을 조정합니다.", summary = "회원 권한 조정(내부 서버용) - 더미")
    public ResponseEntity<Response<NoneResponse>> promoteUser(@RequestHeader HttpHeaders headers)
    {
        Long memberId = HeaderUtil.getMemberId(headers);

        SuccessResponse<NoneResponse> response = memberService.promoteUser(memberId);
        return Response.success(response);
    }

}
