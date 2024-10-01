package kr.co.olivepay.auth.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResponseCode {

    // Member API
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "토큰과 일치하는 회원을 찾을 수 없습니다."),

    // Auth API
    INVALID_LOGIN_REQUEST(HttpStatus.BAD_REQUEST, "전화번호 또는 패스워드 오류입니다."),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),

    // Common Error Code
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부적 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요청을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메소드 입니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
