package kr.co.olivepay.core.transaction.util;

import java.util.UUID;

public class KeyGenerator {
    
    //트랜잭션 관리를 위한 랜덤 키 생성 유틸 메서드
    public static String makeKey() {
        return UUID.randomUUID()
                   .toString()
                   .replace("-", "");
    }
}
