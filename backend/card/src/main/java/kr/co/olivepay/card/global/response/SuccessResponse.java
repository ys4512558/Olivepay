package kr.co.olivepay.card.global.response;

import kr.co.olivepay.card.global.enums.SuccessCode;

public record SuccessResponse<T>(SuccessCode successCode, T data) {
}
