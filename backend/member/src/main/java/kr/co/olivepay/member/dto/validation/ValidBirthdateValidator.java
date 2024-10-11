package kr.co.olivepay.member.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidBirthdateValidator implements ConstraintValidator<ValidBirthdate, String> {

    @Override
    public boolean isValid(String birthdate, ConstraintValidatorContext context) {
        if (birthdate == null || birthdate.length() != 8) {
            return false;
        }

        try {
            // 생년월일을 LocalDate로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthDateParsed = LocalDate.parse(birthdate, formatter);

            // 기준일: 2005년 1월 1일 이후로만 유효
            LocalDate thresholdDate = LocalDate.of(2004, 12, 31);

            // 오늘 날짜 기준으로 생일이 지났는지 확인
            return birthDateParsed.isAfter(thresholdDate);
        } catch (Exception e) {
            return false;
        }
    }
}