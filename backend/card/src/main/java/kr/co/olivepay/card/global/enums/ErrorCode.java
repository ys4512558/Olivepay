package kr.co.olivepay.card.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

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
    CARD_DUPLICATE(HttpStatus.CONFLICT, "이미 등록된 카드입니다."),
    CARD_NOT_EXIST(HttpStatus.BAD_REQUEST, "카드가 존재하지 않습니다."),
    CARDCOMPANY_NOT_EXIST(HttpStatus.BAD_REQUEST, "해당 카드사는 지원하지 않습니다."),
    DEFAULT_CARD_DUPLICATE(HttpStatus.CONFLICT, "꿈나무 카드를 이미 등록하셨습니다."),
    DREAM_CARD_CAN_NOT_DELETE(HttpStatus.FORBIDDEN, "꿈나무 카드는 삭제할 수 없습니다."),

    // Common Error Code
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부적 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요청을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메소드 입니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
