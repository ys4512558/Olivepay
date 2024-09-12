package kr.co.olivepay.card.global.utils;

import kr.co.olivepay.card.global.properties.FintechProperties;
import kr.co.olivepay.card.openapi.dto.req.FintechHeaderReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                               .apiKey(fintechProperties.getApiKey())
                               .apiName(apiName)
                               .transmissionDate(generateTransmissionDate())
                               .transmissionTime(generateTransmissionTime())
                               .institutionCode(fintechProperties.getInstitutionCode())
                               .fintechAppNo(fintechProperties.getFintechAppNo())
                               .apiServiceCode(apiName)
                               .institutionTransactionUniqueNo(generateInstitutionTransactionUniqueNo())
                               .userKey(userKey)
                               .build();
    }

    /**
     * 핀테크 요청 시 필요한 institutionTransactionUniqueNo를 만들어주는 메서드
     *
     * @return 2024/02/15/12:12:12:123579
     */
    private String generateInstitutionTransactionUniqueNo() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS"));
    }

    /**
     * 핀테크 요청 시 필요한 transmissionDate를 만들어주는 메서드
     *
     * @return
     */
    private String generateTransmissionDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 핀테크 요청 시 필요한 transmissionTime를 만들어주는 메서드
     *
     * @return
     */
    private String generateTransmissionTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("HHmmss"));
    }
}
