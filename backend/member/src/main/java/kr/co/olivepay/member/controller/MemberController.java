package kr.co.olivepay.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Size;
import kr.co.olivepay.member.dto.res.DuplicateRes;
import kr.co.olivepay.member.dto.res.UserKeyRes;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kr.co.olivepay.member.global.enums.SuccessCode.GET_USER_KEY_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class MemberController {

    @Value("${config.fintech.userKeyDummy}")
    private String userKey;
    private final MemberService memberService;

    @GetMapping("/members/duplicates/phone")
    @Operation(description = "전화번호를 받아 중복을 체크합니다.", summary = "전화번호 중복 체크")
    public ResponseEntity<Response<DuplicateRes>> checkPhoneNumberDuplicate(
            @RequestParam
            @Size(min = 11, max = 11) String phoneNumber)
    {
        SuccessResponse<DuplicateRes> response = memberService.checkPhoneNumberDuplicate(phoneNumber);
        return Response.success(response);
    }

    @GetMapping("/user-key")
    @Operation(description = "금유망 API의 UserKey를 반환합니다.", summary = "금융망 API UserKey 조회 - 더미")
    public ResponseEntity<Response<UserKeyRes>> getUserKey()
    {
        UserKeyRes userKeyRes = UserKeyRes.builder()
                                          .UserKey(userKey)
                                          .build();
        SuccessResponse<UserKeyRes> response = new SuccessResponse<>(GET_USER_KEY_SUCCESS, userKeyRes);

        return Response.success(response);
    }
}
