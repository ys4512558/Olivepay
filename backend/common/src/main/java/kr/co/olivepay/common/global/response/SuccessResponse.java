package kr.co.olivepay.common.global.response;

import kr.co.olivepay.common.global.enums.SuccessCode;

public record SuccessResponse<T>(SuccessCode successCode, T data) {
}
