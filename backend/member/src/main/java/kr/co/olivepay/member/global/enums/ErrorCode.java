package kr.co.olivepay.member.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResponseCode {

    // Member Error Code
    PHONE_NUMBER_DUPLICATED(HttpStatus.CONFLICT, "이미 가입한 전화번호 입니다."),

    // User Error Code
    PROMOTE_DUPLICATED(HttpStatus.CONFLICT, "이미 일반 유저로 전환한 회원입니다."),
    PIN_LOCKED(HttpStatus.LOCKED, "간편 결제 비밀번호를 재설정 해야합니다."),

    // Common Error Code
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부적 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요청을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메소드 입니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."),

    // Franchise Error Code
    FRANCHISE_REGISTRATION_NUMBER_DUPLICATED(HttpStatus.CONFLICT, "이미 가맹점으로 등록되어 있는 가게 입니다."),

    // fintech API
    FINTECH_API_INVALID_DATA_FORMAT(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (빈 데이터이거나 형식에 맞지 않는 데이터입니다.)"),
    FINTECH_API_ID_ALREADY_EXISTS(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (이미 존재하는 ID 입니다.)"),
    FINTECH_API_ID_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (존재하지 않는 ID 입니다.)"),
    FINTECH_API_USER_KEY_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (존재하지 않는 API KEY 입니다.)"),
    FINTECH_API_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
