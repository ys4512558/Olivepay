package kr.co.olivepay.donation.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

    // Donation API
    DONATION_SUCCESS(HttpStatus.CREATED, "후원이 성공적으로 완료되었습니다."),

    // Donor API
    DONATION_TOTAL_SUCCESS(HttpStatus.OK, "후원 통합 현황 조회가 성공적으로 완료되었습니다."),
    DONATION_MY_SUCCESS(HttpStatus.OK, "후원 내역이 성공적으로 조회되었습니다."),

    // Coupon API
    COUPON_GET_SUCCESS(HttpStatus.OK, "가맹점 쿠폰 조회가 성곡적으로 완료되었습니다."),
    COUPON_LIST_GET_SUCCESS(HttpStatus.OK, "가맹점 리스트 쿠폰 조회가 성공적으로 완료되었습니다."),
    COUPON_MY_LIST_GET_SUCCESS(HttpStatus.OK, "사용자의 쿠폰 리스트 조회가 성공적으로 완료되었습니다."),

    // BASE API
    SUCCESS(HttpStatus.OK, "조회가 성공적으로 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
