package kr.co.olivepay.core.common.dto.req;

import lombok.Builder;

import java.text.SimpleDateFormat;
import java.util.Date;

@Builder
public record CouponUseHistory(
        String couponUnit,
        Date date
) {
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        @Override
        public String toString() {
                return dateFormat.format(date) + " : " + couponUnit + "원권 사용";
        }
}
