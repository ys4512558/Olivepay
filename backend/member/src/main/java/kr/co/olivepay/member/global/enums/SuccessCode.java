package kr.co.olivepay.member.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

    // Member API
    CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS(HttpStatus.OK, "전화번호 중복 조회가 성공적으로 완료되었습니다."),

    // User API
    USER_CREATED(HttpStatus.CREATED, "유저 생성이 완료되었습니다."),

    // BASE API
    SUCCESS(HttpStatus.OK, "조회가 성공적으로 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
