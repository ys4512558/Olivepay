package kr.co.olivepay.card.openapi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.card.global.enums.ErrorCode;
import kr.co.olivepay.card.global.handler.AppException;
import kr.co.olivepay.card.openapi.dto.res.error.FintechErrorRes;
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
public class FintechResponseErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return super.hasError(response);
    }

    /**
     * response를 통해 DefaultResponseErrorHandler(상위 클래스) 의 getResponseBody를 통해 byte[]형태로 body를 받아온 후
     * 이를 FintechErrorRes로 변환하여 responseCode에 알맞는 예외로 변환하여 에러 핸들링.
     *
     * @param response
     * @throws IOException
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        byte[] responseBody = getResponseBody(response);
        FintechErrorRes errorResponse = getErrorResponse(responseBody);
        handleError(errorResponse);
    }


    /**
     * API 응답에 대한 responseCode를 추출하고
     * 이를 통해 API 호출 시 발생한 예외를 핸들링.
     *
     * @param fintechErrorRes
     */
    private void handleError(FintechErrorRes fintechErrorRes) {
        String responseCode = fintechErrorRes.responseCode();
        switch (responseCode) {
            case "H1000":
                throw new AppException(ErrorCode.FINTECH_API_HEADER_INVALID);
            case "H1001":
                throw new AppException(ErrorCode.FINTECH_API_APINAME_INVALID);
            case "H1002":
                throw new AppException(ErrorCode.FINTECH_API_TRANSMISSIONDATE_INVALID);
            case "H1003":
                throw new AppException(ErrorCode.FINTECH_API_TRANSMISSIONTIME_INVALID);
            case "H1004":
                throw new AppException(ErrorCode.FINTECH_API_INSTITUTIONCODE_INVALID);
            case "H1005":
                throw new AppException(ErrorCode.FINTECH_API_FINTECHAPPNO_INVALID);
            case "H1006":
                throw new AppException(ErrorCode.FINTECH_API_APISERVICECODE_INVALID);
            case "H1007":
                throw new AppException(ErrorCode.FINTECH_API_INSTITUTIONTRANSACTIONUNIQUENO_DUPLICATE);
            case "H1008":
                throw new AppException(ErrorCode.FINTECH_API_APIKEY_INVALID);
            case "H1009":
                throw new AppException(ErrorCode.FINTECH_API_USERKEY_INVALID);
            case "H1010":
                throw new AppException(ErrorCode.FINTECH_API_INSTITUTIONTRANSACTIONUNIQUENO_INVALID);
            case "H1019":
                throw new AppException(ErrorCode.FINTECH_API_PRODUCT_NOT_EXIST);
            case "H1023":
                throw new AppException(ErrorCode.FINTECH_API_PRODUCTCODE_INVALID);
            case "Q1000":
                throw new AppException(ErrorCode.FINTECH_API_REQUEST);
        }
    }

    /**
     * 받은 Body의 내용을 통해 서비스에서 처리하기 위한
     * FintechErrorRes로 변환하여 반환
     *
     * @param responseBody
     * @return 반환된 에러에 대한 처리를 위한 FintechErrorRes로 변환하여 반환
     */
    private FintechErrorRes getErrorResponse(@Nullable byte[] responseBody) {
        if (responseBody == null || responseBody.length == 0) {
            return FintechErrorRes.builder()
                                  .responseCode("Q1000")
                                  .responseMessage("예상치 못한 에러가 발생했습니다.")
                                  .build();
        }
        try {
            // byte[]를 JSON으로 간주하고 FintechErrorRes 객체로 변환
            return objectMapper.readValue(responseBody, FintechErrorRes.class);
        } catch (Exception e) {
            log.error("핀테크 API 호출 과정에서 예외가 발생하였습니다. : {} ", e.getMessage());
            return FintechErrorRes.builder()
                                  .responseCode("Q1000")
                                  .responseMessage("예상치 못한 에러가 발생했습니다.")
                                  .build();
        }
    }
}
