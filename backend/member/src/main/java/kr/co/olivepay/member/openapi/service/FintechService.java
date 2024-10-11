package kr.co.olivepay.member.openapi.service;


import kr.co.olivepay.member.openapi.dto.res.member.MemberCreateAndSearchRes;

public interface FintechService {

    /**
     * 계정 생성
     *
     * @param userId: 유저 ID
     * @return 생성된 유저 반환
     */
    MemberCreateAndSearchRes createMember(String userId);

    /**
     * 계정 조회
     *
     * @param userId: 유저 ID
     * @return 조회된 유저 반환
     */
    MemberCreateAndSearchRes searchMember(String userId);
}
