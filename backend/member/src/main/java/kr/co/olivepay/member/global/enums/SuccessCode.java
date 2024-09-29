package kr.co.olivepay.member.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

    // Member API
    CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS(HttpStatus.OK, "전화번호 중복 조회가 성공적으로 완료되었습니다."),
    GET_USER_KEY_SUCCESS(HttpStatus.OK, "유저 금융망 KEY 조회가 성공적으로 완료되었습니다."),
    GET_MEMBER_ROLE_SUCCESS(HttpStatus.OK, "회원 권한 조회가 성공적으로 완료되었습니다."),

    // User API
    USER_CREATED(HttpStatus.CREATED, "유저 생성이 완료되었습니다."),
    USER_PROMOTE_SUCCESS(HttpStatus.CREATED, "일반 유저로 전환이 성공적으로 완료 되었습니다."),
    PASSWORD_CHECK_SUCCESS(HttpStatus.OK, "비밀번호 확인이 성공적으로 완료되었습니다."),
    PASSWORD_CHANGE_SUCCESS(HttpStatus.CREATED, "비밀번호 변경이 성공적으로 완료되었습니다."),

    // Owner API
    OWNER_CREATED(HttpStatus.CREATED, "가맹점주 생성이 완료되었습니다."),

    // BASE API
    SUCCESS(HttpStatus.OK, "조회가 성공적으로 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
