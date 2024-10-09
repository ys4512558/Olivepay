package kr.co.olivepay.common.openapi.dto.res;

import lombok.Builder;

@Builder
public record ClovaRes(

        String inferResult,
        String number,
        String validThru

) { }