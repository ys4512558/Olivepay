package kr.co.olivepay.auth.global.response;

import kr.co.olivepay.auth.global.enums.SuccessCode;

public record SuccessResponse<T>(SuccessCode successCode, T data) {
}
