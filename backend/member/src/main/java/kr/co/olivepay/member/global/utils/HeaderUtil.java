package kr.co.olivepay.member.global.utils;

import kr.co.olivepay.core.util.CommonUtil;
import kr.co.olivepay.member.global.handler.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import static kr.co.olivepay.member.global.enums.ErrorCode.INTERNAL_SERVER_ERROR;

@Slf4j
public class HeaderUtil {

    /**
     * 헤더에서 memberId를 추출하는 메소드 및 에러 핸들링
     * @param headers
     * @return
     */
    public static Long getMemberId(HttpHeaders headers){
        try {
            return CommonUtil.getMemberId(headers);
        } catch (Exception e){
            log.error("헤더에서 memberId를 추출하는 중 오류가 발생했습니다: {}", e.getMessage());
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }
}
