package kr.co.olivepay.member.openapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FintechRequestURI {

    // 유저 생성 요청 URL
    CREATE_MEMBER("member"),
    // 유저 조회 요청 URL
    SEARCH_MEMBER("member/search");

    private final String uri;
}
