package kr.co.olivepay.payment.global.response;

import kr.co.olivepay.payment.global.enums.SuccessCode;

public record SuccessResponse<T>(SuccessCode successCode, T data) {
}
