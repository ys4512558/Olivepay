package kr.co.olivepay.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import kr.co.olivepay.core.member.dto.req.UserNicknamesReq;
import kr.co.olivepay.core.member.dto.req.UserPinCheckReq;
import kr.co.olivepay.core.member.dto.res.UserKeyRes;
import kr.co.olivepay.core.member.dto.res.UserNicknamesRes;
import kr.co.olivepay.member.dto.req.UserInfoChangeReq;
import kr.co.olivepay.member.dto.req.UserPasswordChangeReq;
import kr.co.olivepay.member.dto.req.UserPasswordCheckReq;
import kr.co.olivepay.member.dto.req.UserPinChangeReq;
import kr.co.olivepay.member.dto.res.UserInfoRes;
import kr.co.olivepay.member.dto.res.UserPasswordCheckRes;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.global.utils.HeaderUtil;
import kr.co.olivepay.member.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members/users")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @PostMapping("/password-check")
    @Operation(description = "유저 정보 변경 이전, 비밀번호를 입력받아 일치하는지 확인합니다.", summary = "유저 비밀번호 검증")
    public ResponseEntity<Response<UserPasswordCheckRes>> checkUserPassword(
            @RequestHeader HttpHeaders headers,
            @RequestBody @Valid UserPasswordCheckReq request)
    {
        Long memberId = HeaderUtil.getMemberId(headers);

        SuccessResponse<UserPasswordCheckRes> response = userInfoService.checkUserPassword(memberId, request);
        return Response.success(response);
    }

    @PostMapping("/password-change")
    @Operation(description = "유저의 비밀번호를 변경합니다.", summary = "유저 비밀번호 변경")
    public ResponseEntity<Response<NoneResponse>> changeUserPassword(
            @RequestHeader HttpHeaders headers,
            @RequestBody @Valid UserPasswordChangeReq request)
    {
        Long memberId = HeaderUtil.getMemberId(headers);

        SuccessResponse<NoneResponse> response = userInfoService.changeUSerPassword(memberId, request);
        return Response.success(response);
    }

    @PostMapping("/pin-check")
    @Operation(description = "결제 이전, 간편 결제 비밀번호를 입력받아 일치하는지 확인합니다. 3회 이상 틀리면 재설정 해야합니다.",
            summary = "유저 간편 결제 비밀번호 검증")
    public ResponseEntity<Response<UserKeyRes>> checkUserPin(
            @RequestHeader HttpHeaders headers,
            @RequestBody @Valid UserPinCheckReq request)
    {
        Long memberId = HeaderUtil.getMemberId(headers);

        SuccessResponse<UserKeyRes> response = userInfoService.checkUserPin(memberId, request);
        return Response.success(response);
    }

    @PostMapping("/pin")
    @Operation(description = "간편 결제 비밀번호를 변경합니다.", summary = "간편 결제 비밀번호 변경")
    public ResponseEntity<Response<NoneResponse>> changeUserPin(
            @RequestHeader HttpHeaders headers,
            @RequestBody @Valid UserPinChangeReq request)
    {
        Long memberId = HeaderUtil.getMemberId(headers);

        SuccessResponse<NoneResponse> response = userInfoService.changeUserPin(memberId, request);
        return Response.success(response);
    }

    @GetMapping
    @Operation(description = "유저의 정보를 조회합니다.", summary = "유저 정보 조회")
    public ResponseEntity<Response<UserInfoRes>> getUserInfo(
        @RequestHeader HttpHeaders headers)
    {
        Long memberId = HeaderUtil.getMemberId(headers);

        SuccessResponse<UserInfoRes> response = userInfoService.getUserInfo(memberId);
        return Response.success(response);
    }

    @PostMapping
    @Operation(description = "유저의 정보(닉네임)를 수정합니다.", summary = "유저 정보 수정")
    public ResponseEntity<Response<NoneResponse>> modifyUserInfo(
            @RequestHeader HttpHeaders headers,
            @RequestBody @Valid UserInfoChangeReq request)
    {
        Long memberId = HeaderUtil.getMemberId(headers);

        SuccessResponse<NoneResponse> response = userInfoService.modifyUserInfo(memberId, request);
        return Response.success(response);
    }

    @PostMapping("/nickname")
    @Operation(description = "유저의 닉네임 목록을 조회합니다.",
            summary = "유저 닉네임 목록 조회(내부 서버용)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "회원(유저) 정보",
                required = true,
                content = @Content(
                        examples = {
                                @ExampleObject(
                                        value = """
                                                {
                                                  "memberIds": [
                                                    9,10,20
                                                  ]
                                                }
                                                """
                                )
                        }
                )
            )
    )
    public ResponseEntity<Response<UserNicknamesRes>> getUserNicknames(
            @RequestBody @Valid UserNicknamesReq request)
    {
        SuccessResponse<UserNicknamesRes> response = userInfoService.getUserNicknames(request);
        return Response.success(response);
    }
}
