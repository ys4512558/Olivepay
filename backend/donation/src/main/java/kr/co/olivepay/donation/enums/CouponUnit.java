package kr.co.olivepay.donation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CouponUnit {
    TWO(2000), FOUR(4000);
    final Integer value;

    /**
     * value 를 통해 CouponUnit 을 찾는 메소드
     * @param value 찾고자 하는 CouponUnit 의 value
     * @return 알맞는 CouponUnit
     */
    public static CouponUnit findByValue(String value) {
        for (CouponUnit unit : CouponUnit.values()) {
            if (unit.getValue().toString().equals(value)) {
                return unit;
            }
        }
        return null;
    }
}
