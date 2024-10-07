package kr.co.olivepay.payment.global.enums;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

    // API

    // BASE API
    SUCCESS(HttpStatus.OK, "조회가 성공적으로 완료되었습니다."),

    // FRANCHISE API
    FRANCHISE_REGISTER_SUCCESS(HttpStatus.CREATED, "가맹점이 성공적으로 등록되었습니다."),
    FRANCHISE_SEARCH_SUCCESS(HttpStatus.OK, "가맹점이 성공적으로 검색되었습니다."),
    FRANCHISE_DETAIL_SUCCESS(HttpStatus.OK, "가맹점 상세 정보를 성공적으로 조회하였습니다."),
    QR_CREATE_SUCCESS(HttpStatus.OK, "QR 코드를 성공적으로 생성하였습니다."),
    REGISTRATION_NUMBER_CHECK_SUCCESS(HttpStatus.OK, "사업자등록번호를 성공적으로 조회하였습니다."),

    LIKED_FRANCHISE_SEARCH_SUCCESS(HttpStatus.OK, "좋아요를 눌렀던 가맹점들을 성공적으로 조회하였습니다."),
    LIKE_TOGGLE_ON_SUCCESS(HttpStatus.OK, "좋아요를 성공적으로 활성화하였습니다."),
    LIKE_TOGGLE_OFF_SUCCESS(HttpStatus.OK, "좋아요를 성공적으로 비활성화하였습니다."),

    REVIEW_REGISTER_SUCCESS(HttpStatus.OK, "리뷰를 성공적으로 등록하였습니다."),
    REVIEW_DELETE_SUCCESS(HttpStatus.OK, "리뷰를 성공적으로 삭제하였습니다."),
    USER_REVIEW_SEARCH_SUCCESS(HttpStatus.OK, "사용자의 리뷰를 성공적으로 조회하였습니다."),
    FRANCHISE_REVIEW_SEARCH_SUCCESS(HttpStatus.OK, "가맹점의 리뷰를 성공적으로 조회하였습니다."),
    AVAILABLE_REVIEW_SEARCH_SUCCESS(HttpStatus.OK, "작성하지 않은 리뷰를 성공적으로 조회하였습니다."),
    
    //PAYMENT API
    PAYMENT_REGISTER_SUCCESS(HttpStatus.OK, "결제를 성공했습니다."),
    USER_PAYMENT_HISTORY_SUCCESS(HttpStatus.OK, "유저 결제 내역을 성공적으로 조회하였습니다."),
    FRANCHISE_PAYMENT_HISTORY_SUCCESS(HttpStatus.OK, "가맹점 결제 내역을 성공적으로 조회하였습니다."),
    RECENT_PAYMENT_SEARCH_SUCCESS(HttpStatus.OK, "최근 결제 내역을 성공적으로 조회하였습니다.");
    
    private final HttpStatus httpStatus;
    private final String message;


}
