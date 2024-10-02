package kr.co.olivepay.funding.global.utils;

import kr.co.olivepay.funding.global.handler.AppException;
import kr.co.olivepay.funding.global.response.Response;
import lombok.extern.slf4j.Slf4j;

import static kr.co.olivepay.funding.global.enums.ErrorCode.INTERNAL_SERVER_ERROR;

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
