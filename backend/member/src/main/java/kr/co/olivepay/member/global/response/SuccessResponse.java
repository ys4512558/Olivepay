package kr.co.olivepay.member.global.response;

import kr.co.olivepay.member.global.enums.SuccessCode;

public record SuccessResponse<T>(SuccessCode successCode, T data) {
}
