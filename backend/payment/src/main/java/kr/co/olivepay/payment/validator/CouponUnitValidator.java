package kr.co.olivepay.payment.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CouponUnitValidator implements ConstraintValidator<ValidCouponUnit, Long> {
	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		return value == null || value == 2000L || value == 4000L;
	}
}