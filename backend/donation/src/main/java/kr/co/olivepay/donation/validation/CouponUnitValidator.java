package kr.co.olivepay.donation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kr.co.olivepay.donation.enums.CouponUnit;

public class CouponUnitValidator implements ConstraintValidator<ValidCouponUnit, String> {
    @Override
    public void initialize(ValidCouponUnit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals(CouponUnit.TWO.getValue().toString()) ||
                value.equals(CouponUnit.FOUR.getValue().toString());
    }
}
