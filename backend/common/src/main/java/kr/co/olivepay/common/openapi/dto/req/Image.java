package kr.co.olivepay.common.openapi.dto.req;

import lombok.Builder;

@Builder
public record Image(
        String format,
        String name,
        String data
) { }
