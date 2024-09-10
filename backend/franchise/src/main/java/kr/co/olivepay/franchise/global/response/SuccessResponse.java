package kr.co.olivepay.franchise.global.response;

import kr.co.olivepay.franchise.global.enums.SuccessCode;

public record SuccessResponse<T>(SuccessCode successCode, T data) {
}
