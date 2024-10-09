package kr.co.olivepay.common.openapi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.common.global.enums.ErrorCode;
import kr.co.olivepay.common.global.handler.AppException;
import kr.co.olivepay.common.openapi.dto.res.ClovaErrorRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClovaResponseErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return super.hasError(response);
    }

    /**
     * response를 통해 DefaultResponseErrorHandler(상위 클래스) 의 getResponseBody를 통해 byte[]형태로 body를 받아온 후
     * 이를 ClovaErrorRes로 변환하여 responseCode에 알맞는 예외로 변환하여 에러 핸들링.
     *
     * @param response
     * @throws IOException
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        byte[] responseBody = getResponseBody(response);
        ClovaErrorRes errorResponse = getErrorResponse(responseBody);
        handleError(errorResponse);
    }


    /**
     * API 응답에 대한 responseCode를 추출하고
     * 이를 통해 API 호출 시 발생한 예외를 핸들링.
     *
     * @param clovaErrorRes
     */
    private void handleError(ClovaErrorRes clovaErrorRes) {
        String responseCode = clovaErrorRes.errorCode();
        switch (responseCode) {
            case "100":
                throw new AppException(ErrorCode.CLOVA_API_BAD_REQUEST);
            case "200":
                throw new AppException(ErrorCode.CLOVA_API_AUTHENTICATION_FAILED);
            case "210":
                throw new AppException(ErrorCode.CLOVA_API_PERMISSION_DENIED);
            case "300":
                throw new AppException(ErrorCode.CLOVA_API_NOT_FOUND);
            case "400":
                throw new AppException(ErrorCode.CLOVA_API_QUOTA_EXCEEDED);
            case "410":
                throw new AppException(ErrorCode.CLOVA_API_THROTTLE_LIMITED);
            case "420":
                throw new AppException(ErrorCode.CLOVA_API_RATE_LIMITED);
            case "430":
                throw new AppException(ErrorCode.CLOVA_API_REQUEST_ENTITY_TOO_LARGE);
            case "500":
                throw new AppException(ErrorCode.CLOVA_API_ENDPOINT_ERROR);
            case "510":
                throw new AppException(ErrorCode.CLOVA_API_ENDPOINT_TIMEOUT);
            case "900":
                throw new AppException(ErrorCode.CLOVA_API_UNEXPECTED_ERROR);
            case "0001":
                throw new AppException(ErrorCode.CLOVA_INVALID_URL);
            case "0002":
                throw new AppException(ErrorCode.CLOVA_SECRET_KEY_VALIDATE_FAILED);
            case "0011":
                throw new AppException(ErrorCode.CLOVA_REQUEST_BODY_INVALID);
            case "0021":
                throw new AppException(ErrorCode.CLOVA_PROTOCOL_VERSION_NOT_SUPPORT);
            case "0022":
                throw new AppException(ErrorCode.CLOVA_REQUEST_DOMAIN_INVALID);
            case "0023":
                throw new AppException(ErrorCode.CLOVA_API_REQUEST_COUNT_LIMIT);
            case "0025":
                throw new AppException(ErrorCode.CLOVA_RATE_LIMIT_EXCEEDED);
            case "0500":
                throw new AppException(ErrorCode.CLOVA_UNKNOWN_SERVICE_ERROR);
            case "0501":
                throw new AppException(ErrorCode.CLOVA_OCR_SERVICE_ERROR);
            case "1021":
                throw new AppException(ErrorCode.CLOVA_NOT_FOUND_DEPLOY_INFO);
        }
    }

    /**
     * 받은 Body의 내용을 통해 서비스에서 처리하기 위한
     * ClovaErrorRes로 변환하여 반환
     *
     * @param responseBody
     * @return 반환된 에러에 대한 처리를 위한 ClovaErrorRes로 변환하여 반환
     */
    private ClovaErrorRes getErrorResponse(@Nullable byte[] responseBody) {
        if (responseBody == null || responseBody.length == 0) {
            return ClovaErrorRes.builder()
                                  .errorCode("100")
                                  .message("CLOVA API 호출 중 예상치 못한 에러가 발생했습니다.")
                                  .build();
        }
        try {
            // byte[]를 JSON으로 간주하고 ClovaErrorRes 객체로 변환
            return objectMapper.readValue(responseBody, ClovaErrorRes.class);
        } catch (Exception e) {
            log.error("컨버팅 과정에서 예외가 발생하였습니다. : {} ", e.getMessage());
            return ClovaErrorRes.builder()
                                  .errorCode("100")
                                  .message("CLOVA API 호출 중 예상치 못한 에러가 발생했습니다.")
                                  .build();
        }
    }
}
