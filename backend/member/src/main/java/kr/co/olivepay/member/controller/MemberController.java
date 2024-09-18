package kr.co.olivepay.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kr.co.olivepay.member.dto.res.DuplicateRes;
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
    @Operation(description = "전화번호를 받아 중복을 체크합니다.", summary = "전화번호 중복 체크")
    public ResponseEntity<Response<DuplicateRes>> checkPhoneNumberDuplicate(
            @RequestParam @NotBlank
            @Size(min = 11, max = 11) String phoneNumber)
    {
        SuccessResponse<DuplicateRes> response = memberService.checkPhoneNumberDuplicate(phoneNumber);
        return Response.success(response);
    }
}
