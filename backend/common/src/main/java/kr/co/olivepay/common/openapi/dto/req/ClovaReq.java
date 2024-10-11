package kr.co.olivepay.common.openapi.dto.req;

import lombok.Builder;

import java.util.List;

@Builder
public record ClovaReq(
    String version,
    String requestId,
    Long timestamp,
    List<Image> images
) { }
