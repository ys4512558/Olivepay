package kr.co.olivepay.franchise.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.co.olivepay.franchise.entity.Category;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {

	@Override
	public void initialize(ValidCategory constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true; // null 체크는 @NotNull이 처리
		}
		try {
			Category.valueOf(value.toUpperCase());
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}