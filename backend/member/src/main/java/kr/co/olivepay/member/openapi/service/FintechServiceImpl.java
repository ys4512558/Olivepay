package kr.co.olivepay.member.openapi.service;

import kr.co.olivepay.member.global.utils.FintechRestTemplateUtils;
import kr.co.olivepay.member.openapi.dto.req.MemberCreateAndSearchReq;
import kr.co.olivepay.member.openapi.dto.res.member.MemberCreateAndSearchRes;
import kr.co.olivepay.member.openapi.enums.FintechRequestURI;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class FintechServiceImpl implements FintechService {

    private static final Logger log = LoggerFactory.getLogger(FintechServiceImpl.class);
    @Value("${config.fintech.fintechURI}")
    private String fintechURI;
    @Value("${config.fintech.apiKey}")
    private String apiKey;
    private final FintechRestTemplateUtils restTemplateUtils;

    /**
     * 계정 생성
     *
     * @param userId: 유저 ID
     * @return 생성된 유저 반환
     */
    @Override
    public MemberCreateAndSearchRes createMember(String userId){
        //실제 요청 Body 에 들어갈 요청 파라미터들 생성
        MemberCreateAndSearchReq request = MemberCreateAndSearchReq.builder()
                                                                   .userId(userId)
                                                                   .apiKey(apiKey)
                                                                   .build();

        //요청하려는 URI 생성 API 서버 주소 + 요청 URL
        URI uri = generateURI(FintechRequestURI.CREATE_MEMBER);
        //유틸 클래스를 통해 API 호출 및 응답코드 반환
        MemberCreateAndSearchRes memberRes =
                restTemplateUtils.postForEntity(uri, request, MemberCreateAndSearchRes.class);

        return memberRes;
    }

    /**
     * 계정 조회
     *
     * @param userId: 유저 ID
     * @return 조회된 유저 반환
     */
    @Override
    public MemberCreateAndSearchRes searchMember(String userId){
        //실제 요청 Body 에 들어갈 요청 파라미터들 생성
        MemberCreateAndSearchReq request = MemberCreateAndSearchReq.builder()
                                                                   .userId(userId)
                                                                   .apiKey(apiKey)
                                                                   .build();

        //요청하려는 URI 생성 API 서버 주소 + 요청 URL
        URI uri = generateURI(FintechRequestURI.SEARCH_MEMBER);
        //유틸 클래스를 통해 API 호출 및 응답코드 반환
        MemberCreateAndSearchRes memberRes =
                restTemplateUtils.postForEntity(uri, request, MemberCreateAndSearchRes.class);

        return memberRes;
    }

    /**
     * 요청하고자 하는 URI를 만들어주는 메서드
     *
     * @param requestURI
     * @return 핀테크 API 요청을 위한 URI
     */
    private URI generateURI(FintechRequestURI requestURI) {
        StringBuilder uriStringBuilder = new StringBuilder(fintechURI);
        uriStringBuilder.append(requestURI.getUri());
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(uriStringBuilder.toString())
                                                          .buildAndExpand();
        return uriComponents.toUri();
    }
}
