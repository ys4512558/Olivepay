package kr.co.olivepay.payment.global.utils;

import static kr.co.olivepay.payment.global.enums.ErrorCode.*;

import kr.co.olivepay.payment.global.handler.AppException;
import kr.co.olivepay.payment.global.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorHandler {

    private static final String ERROR = "ERROR";

    public static <T> void handleFeignError(Response<T> response) {
        String resultCode = response.resultCode();
        if (resultCode.equals(ERROR)) {
            log.error("Feign Client Error : [ErrorCode : {}]", response.resultCode());
            log.error("Feign Client Error : [message : {}]", response.message());
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }
}
