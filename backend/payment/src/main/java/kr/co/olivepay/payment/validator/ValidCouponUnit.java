package kr.co.olivepay.payment.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CouponUnitValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCouponUnit {
	String message() default "유효하지 않은 쿠폰 유닛입니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}