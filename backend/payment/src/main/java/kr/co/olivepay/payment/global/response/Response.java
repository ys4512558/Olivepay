package kr.co.olivepay.payment.global.response;

import org.springframework.http.ResponseEntity;

import kr.co.olivepay.payment.global.enums.ErrorCode;

public record Response<T>(String resultCode, String code, String message, T data) {
    public static <T> ResponseEntity<Response<T>> success(SuccessResponse<T> response) {
        return new ResponseEntity<>(new Response<>("SUCCESS", response.successCode().name(),
                response.successCode().getMessage(), response.data()), response.successCode().getHttpStatus());
    }

    public static <T> Response<T> error(ErrorCode code, T data) {
        return new Response<>("ERROR", code.name(), code.getMessage(), data);
    }


}

