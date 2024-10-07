package kr.co.olivepay.common.validator;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        // 파일이 비어있는지 확인
        if (value == null || value.isEmpty()) {
            return false;
        }

        // 파일이 이미지인지 확인 (MIME 타입 체크)
        return Objects.requireNonNull(value.getContentType()).startsWith("image/");
    }
}
