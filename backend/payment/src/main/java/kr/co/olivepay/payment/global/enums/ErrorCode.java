package kr.co.olivepay.payment.global.enums;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResponseCode {

    // fintech API
    FINTECH_API_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다."),
    FINTECH_API_HEADER_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (HEADER 정보가 유효하지 않습니다.)"),
    FINTECH_API_APINAME_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (API 이름이 유효하지 않습니다.)"),
    FINTECH_API_TRANSMISSIONDATE_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (전송일자 형식이 유효하지 않습니다.)"),
    FINTECH_API_TRANSMISSIONTIME_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (전송시각 형식이 유효하지 않습니다.)"),
    FINTECH_API_INSTITUTIONCODE_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (기관코드가 유효하지 않습니다.)"),
    FINTECH_API_FINTECHAPPNO_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (핀테크 앱 일련번호가 유효하지 않습니다.)"),
    FINTECH_API_APISERVICECODE_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (API 서비스코드가 유효하지 않습니다.)"),
    FINTECH_API_INSTITUTIONTRANSACTIONUNIQUENO_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (기관거래고유번호가 유효하지 않습니다.)"),
    FINTECH_API_INSTITUTIONTRANSACTIONUNIQUENO_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (기관거래고유번호가 중복된 값입니다.)"),
    FINTECH_API_APIKEY_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (API_KEY가 유효하지 않습니다.)"),
    FINTECH_API_USERKEY_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (USER_KEY가 유효하지 않습니다.)"),
    FINTECH_API_PRODUCT_NOT_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (없는 상품입니다.)"),
    FINTECH_API_PRODUCTCODE_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "금융 API 요청 에러가 발생했습니다. (상품고유번호가 존재하지 않습니다.)"),
    ACCOUNT_INVALID(HttpStatus.BAD_REQUEST, "계좌번호가 유효하지 않습니다."),

    // API

    // Common Error Code
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부적 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요청을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메소드 입니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."),

    //Franchise Error Code
    FRANCHISE_REGISTRATION_NUMBER_DUPLICATED(HttpStatus.CONFLICT, "이미 가맹점으로 등록되어 있는 가게 입니다."),
    FRANCHISE_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "가맹점 ID에 해당하는 가맹점을 찾을 수 없습니다."),
    FRANCHISE_NOT_FOUND_BY_OWNER(HttpStatus.NOT_FOUND, "사용자의 가맹점을 찾을 수 없습니다."),
    QR_CREATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "QR 코드 생성 중 오류가 발생했습니다."),

    //Payment Error Code
    OWNERSHIP_REQUIRED(HttpStatus.FORBIDDEN, "해당 기능은 가맹점주만 접근 가능합니다."),
    FRANCHISE_FEIGN_CLIENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"franchsie 서비스 호출 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
