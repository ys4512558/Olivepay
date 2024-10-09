package kr.co.olivepay.common.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

    // OCR API
    OCR_SUCCESS(HttpStatus.CREATED, "OCR 처리에 성공했습니다."),

    // SMS API
    SMS_SEND_SUCCESS(HttpStatus.CREATED, "인증번호가 성공적으로 전송되었습니다."),
    SMS_CHECK_SUCCESS(HttpStatus.CREATED, "인증번호가 성공적으로 인증되었습니다."),

    // EMAIL API
    EMAIL_SEND_SUCCESS(HttpStatus.OK, "이메일이 성공적으로 발송되었습니다."),

    // BASE API
    SUCCESS(HttpStatus.OK, "조회가 성공적으로 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
