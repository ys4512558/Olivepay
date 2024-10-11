package kr.co.olivepay.common.openapi.utils;

import kr.co.olivepay.common.openapi.dto.req.ClovaReq;
import kr.co.olivepay.common.openapi.handler.ClovaResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClovaRestTemplateUtils {

    @Value("${config.clova.url}")
    private String url;
    @Value("${config.clova.secret}")
    private String clientSecret;

    private final ClovaResponseErrorHandler responseErrorHandler;

    /**
     * Clova API Post 요청을 위한 메소드
     * @param request
     * @param responseType
     * @return
     * @param <E>
     */
    public String postForEntity(ClovaReq request) {
        // RestTemplate 생성
        RestTemplate restTemplate = generateRestTemplate();

        // HttpEntity 생성
        HttpEntity<ClovaReq> httpEntity = generateHttpEntity(request);

        // 요청 수행
        ResponseEntity<String> responseEntity =  restTemplate.postForEntity(url, httpEntity, String.class);
        return responseEntity.getBody();
    }

    /**
     * Clova API Rest Template 생성 메소드
     * @return
     */
    private RestTemplate generateRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // 커스텀 에러 핸들러 세팅
        restTemplate.setErrorHandler(responseErrorHandler);

        return restTemplate;
    }

    /**
     * Clova API 호출을 위한 HttpEntity 생성
     * @param request
     * @return
     */
    private HttpEntity<ClovaReq> generateHttpEntity(ClovaReq request){
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-OCR-SECRET", clientSecret);

        // HttpEntity에 요청 바디(문자열)와 헤더 포함
        HttpEntity<ClovaReq> httpEntity = new HttpEntity<>(request, headers);

        return httpEntity;
    }
}
