package kr.co.olivepay.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @Operation(description = "유저 정보를 받아 회원가입 합니다.", summary = "유저 회원가입")
    public ResponseEntity<Response<NoneResponse>> registerUser(
            @RequestBody @Valid UserRegisterReq request)
    {
        SuccessResponse<NoneResponse> response = userService.registerUser(request);
        return Response.success(response);
    }
}
