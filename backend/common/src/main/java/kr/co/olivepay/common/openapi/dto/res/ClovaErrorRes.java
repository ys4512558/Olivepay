package kr.co.olivepay.common.openapi.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record ClovaErrorRes(
        @JsonProperty("errorCode")
        String errorCode,
        @JsonProperty("message")
        String message
) { }


