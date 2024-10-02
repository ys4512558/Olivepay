package kr.co.olivepay.funding.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements ResponseCode {

	// API

	// BASE API
	SUCCESS(HttpStatus.OK, "조회가 성공적으로 완료되었습니다."),

	//FUNDING API
	TOTAL_FUNDING_AMOUNT_SUCCESS(HttpStatus.OK, "공용 기부금 총액을 성공적으로 조회했습니다."),
	FUNDING_USAGE_SEARCH_SUCCESS(HttpStatus.OK, "공용 기부금 사용 내역을 성공적으로 조회했습니다."),
	FUNDING_REGISTER_SUCCESS(HttpStatus.OK, "금액을 공용 기부금으로 성공적으로 이체했습니다."),
	FUNDING_USAGE_REGISTER_SUCCESS(HttpStatus.OK, "기부금을 성공적으로 이체하였습니다.");

	private final HttpStatus httpStatus;
	private final String message;

}
