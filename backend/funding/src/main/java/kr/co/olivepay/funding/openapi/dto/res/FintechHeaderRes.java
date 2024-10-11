package kr.co.olivepay.funding.openapi.dto.res;

import lombok.Builder;

/**
 * 핀테크 API 요청시 반환되는 Header에 들어오는 값
 * 공통적으로 사용 되는 필드를 클래스화하여 사용
 *
 * @param responseCode
 * @param responseMessage
 * @param apiName
 * @param transmissionDate
 * @param transmissionTime
 * @param institutionCode
 * @param apiKey
 * @param apiServiceCode
 * @param institutionTransactionUniqueNo
 */

@Builder
public record FintechHeaderRes(
        String responseCode,
        String responseMessage,
        String apiName,
        String transmissionDate,
        String transmissionTime,
        String institutionCode,
        String apiKey,
        String apiServiceCode,
        String institutionTransactionUniqueNo
) {

}
