package kr.co.olivepay.card.global.utils;

import kr.co.olivepay.card.openapi.dto.req.abstracts.FintechRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Component
@RequiredArgsConstructor
public class FintechRestTemplateUtils {

    /**
     * Content-Type : application/json
     * 의 설정을 가지는 RestTemplate 객체를 생성하는 private 메서드
     *
     * @return RestTemplate
     */
    private RestTemplate generateRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate;
    }

    /**
     * POST 요청 메서드
     *
     * @param uri          : 요청하고자 하는 URI
     * @param request      : 요청 Body에 포함되어야 하는 클래스 <T>객체
     * @param responseType : 응답을 매핑할 클래스의 Class
     * @param <E>          : 응답을 매핑할 클래스
     * @return POST요청에 대한 응답 Body
     */
    public <E> E postForEntity(URI uri, FintechRequest request, Class<E> responseType) {
        RestTemplate restTemplate = generateRestTemplate();
        ResponseEntity<E> responseEntity
                = restTemplate.postForEntity(uri, request, responseType);
        return responseEntity.getBody();

    }
}