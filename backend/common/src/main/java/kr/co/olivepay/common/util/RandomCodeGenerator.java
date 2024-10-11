package kr.co.olivepay.common.util;

import java.util.Random;

public class RandomCodeGenerator {

    public static String generateSixDigitCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        // 0~9 로 이루어진 6자리 숫자(문자열)를 랜덤으로 생성
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }
        return code.toString(); // 문자열 반환
    }
}