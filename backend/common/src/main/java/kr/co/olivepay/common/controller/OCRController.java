package kr.co.olivepay.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.common.dto.res.OCRRes;
import kr.co.olivepay.common.global.enums.ErrorCode;
import kr.co.olivepay.common.global.handler.AppException;
import kr.co.olivepay.common.global.response.Response;
import kr.co.olivepay.common.global.response.SuccessResponse;
import kr.co.olivepay.common.validator.ValidImage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static kr.co.olivepay.common.global.enums.SuccessCode.OCR_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commons")
public class OCRController {


    @PostMapping(path = "/ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "카드 사진을 받아 카드 번호와 유효년월을 반환합니다.", summary = "카드 OCR")
    public ResponseEntity<Response<OCRRes>> register(
            @Valid @RequestPart(value = "cardImg") @ValidImage MultipartFile cardImg
    ) {
        OCRRes ocrRes = OCRRes.builder()
                              .cardNumber("0000111122223333")
                              .expirationMonth("06")
                              .expirationYear("28")
                              .build();

        SuccessResponse<OCRRes> response = new SuccessResponse<>(OCR_SUCCESS, ocrRes);
        return Response.success(response);
    }

}
