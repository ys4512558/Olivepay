package kr.co.olivepay.member.openapi.dto.res.error;

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
public record FintechErrorRes(
        String responseCode,
        String responseMessage
) {
}
