package kr.co.olivepay.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kr.co.olivepay.common.dto.res.OCRRes;
import kr.co.olivepay.common.global.response.Response;
import kr.co.olivepay.common.global.response.SuccessResponse;
import kr.co.olivepay.common.service.OCRService;
import kr.co.olivepay.common.util.HeaderUtil;
import kr.co.olivepay.common.validator.ValidImage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commons")
public class OCRController {

    private final OCRService ocrService;

    @PostMapping(path = "/ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "카드 사진을 받아 카드 번호와 유효년월을 반환합니다.", summary = "카드 OCR")
    public ResponseEntity<Response<OCRRes>> register(
            @RequestHeader HttpHeaders headers,
            @Valid @RequestPart(value = "cardImg") @ValidImage MultipartFile cardImg
    ) {
        Long memberId = HeaderUtil.getMemberId(headers);
        SuccessResponse<OCRRes> response = ocrService.extractText(memberId, cardImg);
        return Response.success(response);
    }

}
