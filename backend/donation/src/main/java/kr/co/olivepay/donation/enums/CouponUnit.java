package kr.co.olivepay.donation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CouponUnit {
    TWO(2000), FOUR(4000);
    Integer value;
}
