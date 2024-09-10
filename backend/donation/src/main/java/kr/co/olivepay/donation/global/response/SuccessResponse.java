package kr.co.olivepay.donation.global.response;

import kr.co.olivepay.donation.global.enums.SuccessCode;

public record SuccessResponse<T>(SuccessCode successCode, T data) {
}
