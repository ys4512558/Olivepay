package kr.co.olivepay.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Size;
import kr.co.olivepay.core.member.dto.res.MemberRoleRes;
import kr.co.olivepay.core.member.dto.res.DuplicateRes;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/duplicates/phone")
    @Operation(description = "전화번호를 받아 중복을 체크합니다.", summary = "전화번호 중복 체크(내부 서버용)")
    public ResponseEntity<Response<DuplicateRes>> checkPhoneNumberDuplicate(
            @RequestParam
            @Size(min = 11, max = 11) String phoneNumber)
    {
        SuccessResponse<DuplicateRes> response = memberService.checkPhoneNumberDuplicate(phoneNumber);
        return Response.success(response);
    }

    @GetMapping("/members/role")
    @Operation(description = "회원 ID를 받아 권한인 확인합니다.", summary = "회원 권한 체크(내부 서버용)")
    public ResponseEntity<Response<MemberRoleRes>> getMemberRole(
            @RequestParam Long memberId)
    {
        SuccessResponse<MemberRoleRes> response = memberService.getMemberRole(memberId);
        return Response.success(response);
    }

}
