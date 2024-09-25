package kr.co.olivepay.gateway.global.response;

import kr.co.olivepay.gateway.global.enums.SuccessCode;

public record SuccessResponse<T>(SuccessCode successCode, T data) {
}
