package kr.co.olivepay.funding.global.response;

import kr.co.olivepay.funding.global.enums.SuccessCode;

public record SuccessResponse<T>(SuccessCode successCode, T data) {
}
