package kr.co.olivepay.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.member.dto.req.OwnerRegisterReq;
import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/sign-up")
    @Operation(description = "가맹점 및 가맹점주 정보를 받아 회원가입 합니다.", summary = "가맹점주 회원가입")
    public ResponseEntity<Response<NoneResponse>> registerOwner(
            @RequestBody @Valid OwnerRegisterReq request)
    {
        SuccessResponse<NoneResponse> response = ownerService.registerOwnerAndFranchise(request);
        return Response.success(response);
    }
}
