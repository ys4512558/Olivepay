package kr.co.olivepay.donation.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResponseCode {
    // Feign API
    FRANCHISE_FEIGN_CLIENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"franchise 서비스 호출 중 오류가 발생했습니다."),

    // COUPON API
    COUPON_IS_NOT_EXIST(HttpStatus.BAD_REQUEST, "쿠폰이 존재하지 않습니다."),
    COUPON_MAX_EXCEED(HttpStatus.BAD_REQUEST, "쿠폰을 더이상 받을 수 없습니다."),
    COUPON_GET_FAIL(HttpStatus.BAD_REQUEST, "쿠폰 다운로드 사용자가 많아 쿠폰 획득에 실패했습니다. 다시 다운로드를 시도하세요."),

    // Common Error Code
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부적 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요청을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메소드 입니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
