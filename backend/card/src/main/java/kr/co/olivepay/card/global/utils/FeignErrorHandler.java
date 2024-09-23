package kr.co.olivepay.card.global.utils;

import kr.co.olivepay.card.global.handler.AppException;
import kr.co.olivepay.card.global.response.Response;

import static kr.co.olivepay.card.global.enums.ErrorCode.INTERNAL_SERVER_ERROR;

public class FeignErrorHandler {

    private static final String ERROR = "ERROR";

    public static <T> void handleFeignError(Response<T> response) {
        String resultCode = response.resultCode();
        if (resultCode.equals(ERROR)) {
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }
}
