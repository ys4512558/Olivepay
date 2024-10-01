package kr.co.olivepay.funding.openapi.dto.req;

import lombok.Builder;

/**
 * 모든 API 요청에 필요한
 * 공통 Header 객체의 필드
 *
 * @param apiName
 * @param transmissionDate
 * @param transmissionTime
 * @param institutionCode
 * @param fintechAppNo
 * @param apiServiceCode
 * @param institutionTransactionUniqueNo
 * @param apiKey
 * @param userKey
 */
@Builder
public record FintechHeaderReq(
        String apiName,
        String transmissionDate,
        String transmissionTime,
        String institutionCode,
        String fintechAppNo,
        String apiServiceCode,
        String institutionTransactionUniqueNo,
        String apiKey,
        String userKey
) {

}
