package kr.co.olivepay.payment.global.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import kr.co.olivepay.payment.global.properties.FintechProperties;
import kr.co.olivepay.payment.openapi.dto.req.FintechHeaderReq;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FinTechHeaderGenerator {

    private final FintechProperties fintechProperties;

    /**
     * 핀테크 API 요청을 위한 헤더 생성 메서드
     *
     * @param apiName
     * @param userKey
     * @return
     */
    public FintechHeaderReq generateFintechAPIHeader(
            final String apiName,
            final String userKey) {
        return FintechHeaderReq.builder()
                               .apiName(apiName)
                               .transmissionDate(generateTransmissionDate())
                               .transmissionTime(generateTransmissionTime())
                               .institutionCode(fintechProperties.getInstitutionCode())
                               .fintechAppNo(fintechProperties.getFintechAppNo())
                               .apiServiceCode(apiName)
                               .institutionTransactionUniqueNo(generateInstitutionTransactionUniqueNo())
                               .apiKey(fintechProperties.getApiKey())
                               .userKey(userKey)
                               .build();
    }

    /**
     * 핀테크 요청 시 필요한 institutionTransactionUniqueNo를 만들어주는 메서드
     *
     * @return 2024/02/15/12:12:12:123579
     */
    private String generateInstitutionTransactionUniqueNo() {
        ZonedDateTime now = getCurrentTime();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS"));
    }

    /**
     * 핀테크 요청 시 필요한 transmissionDate를 만들어주는 메서드
     *
     * @return
     */
    private String generateTransmissionDate() {
        ZonedDateTime now = getCurrentTime();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 핀테크 요청 시 필요한 transmissionTime를 만들어주는 메서드
     *
     * @return
     */
    private String generateTransmissionTime() {
        ZonedDateTime now = getCurrentTime();
        return now.format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    /**
     * 현재 우리나라의 시간을 반환하는 메서드
     */
    private ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
