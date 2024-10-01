package kr.co.olivepay.core.global.dto.res;

public record PageResponse<T>(Long nextIndex, T contents) {
}
