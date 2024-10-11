package kr.co.olivepay.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import kr.co.olivepay.member.dto.req.OwnerRegisterReq;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping(value = "/sign-up")
    @Operation(
            description = "가맹점 및 가맹점주 정보를 받아 회원가입 합니다.",
            summary = "가맹점주 회원가입",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "가맹점주 및 가맹점 정보",
                    required = true,
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value = """
                                            {
                                                "name": "홍길동",
                                                "password": "Password123!",
                                                "phoneNumber": "01011112222",
                                                "registrationNumber": "6663248810",
                                                "franchiseName": "가맹점 이름",
                                                "category": "KOREAN",
                                                "telephoneNumber": "0827658295",
                                                "address": "주소",
                                                "latitude": 45,
                                                "longitude": 135,
                                                "rrnPrefix": "890830",
                                                "rrnCheckDigit": 1
                                            }
                                            """,
                                            description = "가맹점주 및 가맹점 정보 예제"
                                    )
                            }
                    )
            )
    )
    public ResponseEntity<Response<NoneResponse>> registerOwner(
            @RequestBody @Valid OwnerRegisterReq request
    )
    {
        SuccessResponse<NoneResponse> response = ownerService.registerOwnerAndFranchise(request);
        return Response.success(response);
    }
}
