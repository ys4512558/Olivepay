package kr.co.olivepay.auth.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

    // AUTH API
    LOGIN_SUCCESS(HttpStatus.CREATED, "로그인이 성공적으로 완료되었습니다."),
    AUTH_TOKEN_CHANGE_SUCCESS(HttpStatus.CREATED, "토큰 재발급이 성공적으로 완료되었습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃이 성공적으로 완료되었습니다."),

    // BASE API
    SUCCESS(HttpStatus.OK, "조회가 성공적으로 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
