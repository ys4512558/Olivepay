package kr.co.olivepay.card.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

    // API
    CARD_REGISTER_SUCCESS(HttpStatus.OK, "카드가 성공적으로 등록되었습니다."),
    CARD_DELETE_SUCCESS(HttpStatus.OK, "카드가 성공적으로 삭제되었습니다."),
    // BASE API
    SUCCESS(HttpStatus.OK, "조회가 성공적으로 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
