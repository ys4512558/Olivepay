package kr.co.olivepay.common.service;

import kr.co.olivepay.common.dto.res.OCRRes;
import kr.co.olivepay.common.global.response.SuccessResponse;
import org.springframework.web.multipart.MultipartFile;

public interface OCRService {

    /**
     * 카드 사진을 받아 네이버 CLOVA OCR을 통해<br>
     * 카드번호, 유효년월을 받아옵니다.
     * @param cardImg
     * @return
     */
    SuccessResponse<OCRRes> extractText(Long memberId, MultipartFile cardImg);

}
