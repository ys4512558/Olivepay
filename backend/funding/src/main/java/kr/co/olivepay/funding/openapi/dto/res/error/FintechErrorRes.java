package kr.co.olivepay.funding.openapi.dto.res.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * API 호출 시 반환되는 에러 메시지로 Body에
 * {
 * "responseCode":"",
 * "responseMessage":""
 * }
 * 형태로 들어오는 메시지를 객체에 담기 위해 사용
 *
 * @param responseCode
 * @param responseMessage
 */

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record FintechErrorRes(
        @JsonProperty("responseCode")
        String responseCode,
        @JsonProperty("responseMessage")
        String responseMessage
) {
}
