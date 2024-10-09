package kr.co.olivepay.common.openapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.common.global.handler.AppException;
import kr.co.olivepay.common.openapi.dto.req.ClovaReq;
import kr.co.olivepay.common.openapi.dto.req.Image;
import kr.co.olivepay.common.openapi.dto.res.ClovaRes;
import kr.co.olivepay.common.openapi.utils.ClovaRestTemplateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static kr.co.olivepay.common.global.enums.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClovaServiceImpl implements ClovaService{

    private final ObjectMapper objectMapper;
    private final ClovaRestTemplateUtils restTemplateUtils;

    @Override
    public ClovaRes getCardText(MultipartFile cardImg) {
        // 사진 확장자 설정
        String fileExtension = getFileExtension(cardImg);

        // Card img를 Base64 문자열로 인코딩
        String base64Img = toBase64Img(cardImg);

        // Images 생성
        Image image = Image.builder()
                           .format(fileExtension)
                           .name("olivepay")
                           .data(base64Img)
                           .build();
        List<Image> images = List.of(image);
        
        // Request 생성
        ClovaReq request = ClovaReq.builder()
                                   .requestId("olivepay")
                                   .version("V2")
                                   .timestamp(Instant.now().toEpochMilli())
                                   .images(images)
                                   .build();

        // API 요청 Body
        log.info("[CLOVA OCR API] 호출 시작");
        String jsonResponse  = restTemplateUtils.postForEntity(request);
        log.info("[CLOVA OCR API] 호출 종료");

        // API 결과 Parser
        ClovaRes response = responseParser(jsonResponse);

        return response;
    }

    /**
     * img를 Base64 문자열로 인코딩
     * @param img
     * @return
     */
    private String toBase64Img(MultipartFile img){
        try {
            byte[] imageBytes = img.getBytes();
            return Base64.encodeBase64String(imageBytes);
        } catch (IOException e){
            log.error("이미지 Base64 문자열로 변환 중 에러");
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 요청받은 파일의 확장자를 확인하고 <br>
     * jpg, png 가 아닌경우 에러 발생
     * @param img
     * @return
     */
    private String getFileExtension(MultipartFile img){
        String contentType = img.getContentType();

        if (contentType.contains("image/jpeg")) {
            return "jpg";
        } else if (contentType.contains("image/png")) {
            return "png";
        }

        throw  new AppException(INVLAID_IMAGE_FILE);
    }

    /**
     * Clova OCR API 호출에 대한 결과를 분류 합니다.
     * @param jsonResponse
     * @return
     */
    private ClovaRes responseParser(String jsonResponse){

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // 결과 상태 확인, SUCCESS or ERROR
            String inferResult = rootNode.path("images")
                                         .get(0)
                                         .path("inferResult")
                                         .asText();

            // 카드 번호 추출
            String cardNumber = rootNode.path("images")
                                        .get(0).path("creditCard")
                                        .path("result")
                                        .path("number")
                                        .path("text")
                                        .asText();
            
            // 카드 유효년월 추출
            String validThru = rootNode.path("images")
                                       .get(0).path("creditCard")
                                       .path("result")
                                       .path("validThru")
                                       .path("text")
                                       .asText();

            // response 생성
            ClovaRes clovaRes = ClovaRes.builder()
                                        .inferResult(inferResult)
                                        .number(cardNumber)
                                        .validThru(validThru)
                                        .build();

            return clovaRes;

        } catch (Exception e) {
            log.error("JSON 파싱 중 에러", e);
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }
}
