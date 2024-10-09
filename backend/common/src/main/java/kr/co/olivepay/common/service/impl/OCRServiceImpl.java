package kr.co.olivepay.common.service.impl;

import kr.co.olivepay.common.dto.res.OCRRes;
import kr.co.olivepay.common.entity.OCR;
import kr.co.olivepay.common.global.handler.AppException;
import kr.co.olivepay.common.global.response.SuccessResponse;
import kr.co.olivepay.common.openapi.dto.res.ClovaRes;
import kr.co.olivepay.common.openapi.service.ClovaService;
import kr.co.olivepay.common.repository.OCRRepository;
import kr.co.olivepay.common.service.OCRService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static kr.co.olivepay.common.global.enums.ErrorCode.*;
import static kr.co.olivepay.common.global.enums.SuccessCode.OCR_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class OCRServiceImpl implements OCRService {

    private static final int MAX_REQUESTS = 3;
    private static final long TEN_MINUTES = 60*10L;  // 10분 TTL (단위: 초)
    private static final String ERROR = "ERROR";
    private final OCRRepository ocrRepository;
    private final ClovaService clovaService;

    /**
     * 요청받은 사진에서 카드번호, 유효년월을 추출하는 메소드<br>
     * Clova 의 OCR API를 내부적으로 호출해 사용합니다.
     * @param memberId
     * @param cardImg
     * @return
     */
    @Override
    public SuccessResponse<OCRRes> extractText(Long memberId, MultipartFile cardImg) {
        // api 호출 횟수 확인
        OCR ocr = validateApiCallLimit(memberId);

        // CLOVA OCR API 호출
        ClovaRes clovaRes = null;
        try {
            clovaRes = clovaService.getCardText(cardImg);
        } finally {
            // 호출 횟수 업데이트
            ocr.incrementCount();
            ocrRepository.save(ocr);
        }

        // CLOVA OCR API 호출 결과 확인, 실패 시 에러 발생
        if(clovaRes == null || ERROR.equals(clovaRes.inferResult())){
            throw new AppException(INVLAID_IMAGE_FILE);
        }

        // DTO 생성
        String[] validThru = clovaRes.validThru().split("/");
        OCRRes ocrRes = OCRRes.builder()
                              .cardNumber(clovaRes.number())
                              .expirationMonth(validThru[0])
                              .expirationYear(validThru[1])
                              .build();

        return new SuccessResponse<>(OCR_SUCCESS, ocrRes);
    }

    /**
     * 호출 가능 여부를 판단하는 메소드<br>
     * 한 유저당 10분에 3번 API를 호출할 수 있습니다.
     * @param memberId
     * @return
     */
    private OCR validateApiCallLimit(Long memberId) {
        OCR ocr = ocrRepository.findById(memberId).orElseGet(() -> {
            // 새로운 요청일 경우 OCR 객체 생성 및 저장
            OCR newOcr = new OCR(memberId, 0, TEN_MINUTES);
            return newOcr;
        });

        // 호출 횟수 검증
        if (ocr.getCount() >= MAX_REQUESTS) {
            // 호출 횟수 3회를 초과한 경우 예외 처리
            throw new AppException(TOO_MANY_REQUESTS);
        }

        return ocr;
    }
}
