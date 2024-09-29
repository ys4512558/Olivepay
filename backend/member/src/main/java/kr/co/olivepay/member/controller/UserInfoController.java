package kr.co.olivepay.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.core.util.CommonUtil;
import kr.co.olivepay.member.dto.req.UserPasswordChangeReq;
import kr.co.olivepay.member.dto.req.UserPasswordCheckReq;
import kr.co.olivepay.member.dto.res.UserPasswordCheckRes;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.handler.AppException;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.global.utils.HeaderUtil;
import kr.co.olivepay.member.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kr.co.olivepay.member.global.enums.ErrorCode.INTERNAL_SERVER_ERROR;

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

}
