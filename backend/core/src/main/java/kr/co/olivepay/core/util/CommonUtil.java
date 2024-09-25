package kr.co.olivepay.core.util;

import org.springframework.http.HttpHeaders;

/**
 * 공통 유틸리티 클래스입니다.
 */
public class CommonUtil {

    /**
     * HttpHeaders에서 회원 ID를 추출하는 메서드입니다.
     *
     * @param headers HTTP 헤더
     * @return 회원 ID
     */
    public static Long getMemberId(HttpHeaders headers) {
        return Long.valueOf(headers.get("member-id")
                                   .get(0));
    }

    /**
     * HttpHeaders에서 회원 ROLE을 추출하는 메서드입니다.
     *
     * @param headers HTTP 헤더
     * @return 회원 ROLE
     */
    public static String getMemberRole(HttpHeaders headers) {
        return headers.get("member-role").get(0);
    }
}
