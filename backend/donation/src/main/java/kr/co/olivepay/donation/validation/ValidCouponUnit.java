package kr.co.olivepay.donation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CouponUnitValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCouponUnit {
    String message() default "쿠폰 단위는 2000, 4000 중에서 선택 해야합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
