package kr.co.olivepay.common.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResponseCode {

    // OCR API
    TOO_MANY_REQUESTS(HttpStatus.BAD_REQUEST, "요청 횟수를 초과했습니다. 10분 후에 다시 시도해주세요."),

    // CLOVA API
    INVLAID_IMAGE_FILE(HttpStatus.BAD_REQUEST, "유효하지 않은 이미지 파일입니다."),
    CLOVA_API_BAD_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - CLOVA API 호출 중 예상치 못한 에러가 발생했습니다."),
    CLOVA_API_AUTHENTICATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 인증 실패"),
    CLOVA_API_PERMISSION_DENIED(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 권한 없음"),
    CLOVA_API_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 요청한 리소스를 찾을 수 없습니다."),
    CLOVA_API_QUOTA_EXCEEDED(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - Quota 초과"),
    CLOVA_API_THROTTLE_LIMITED(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - Rate 초과"),
    CLOVA_API_RATE_LIMITED(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - Rate 초과"),
    CLOVA_API_REQUEST_ENTITY_TOO_LARGE(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 요청 엔티티 크기 초과"),
    CLOVA_API_ENDPOINT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 엔드포인트 연결 오류"),
    CLOVA_API_ENDPOINT_TIMEOUT(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 엔드포인트 연결 시간 초과"),
    CLOVA_API_UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 예외 처리가 안된 오류"),
    CLOVA_INVALID_URL(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 입력한 호출 URL이 유효하지 않음"),
    CLOVA_SECRET_KEY_VALIDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 입력한 Secret Key가 검증되지 않음"),
    CLOVA_REQUEST_BODY_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 입력한 요청 바디 값이 올바르지 않음"),
    CLOVA_PROTOCOL_VERSION_NOT_SUPPORT(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - API 버전이 지원되지 않음"),
    CLOVA_REQUEST_DOMAIN_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 입력한 요청 도메인 값이 올바르지 않음"),
    CLOVA_API_REQUEST_COUNT_LIMIT(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - API 호출 제한에 도달함"),
    CLOVA_RATE_LIMIT_EXCEEDED(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - API 호출 수가 요금 제한(Rate Limit)을 초과함"),
    CLOVA_UNKNOWN_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - 알 수 없는 서비스 오류가 발생함"),
    CLOVA_OCR_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - OCR 서비스 오류가 발생함"),
    CLOVA_NOT_FOUND_DEPLOY_INFO(HttpStatus.INTERNAL_SERVER_ERROR, "CLOVA API 요청 에러 - Template 배포 이력을 찾을 수 없음"),

    // SMS API
    SMS_CODE_INVALID(HttpStatus.BAD_REQUEST, "인증코드가 유효하지 않습니다"),

    // Common Error Code
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부적 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요청을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP 메소드 입니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."),
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "토큰이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
