package kr.co.olivepay.donation.service;

import kr.co.olivepay.donation.dto.res.CouponUsedStateRes;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponUsedEvent;

public interface DonationEventService {
    CouponUsedStateRes useCoupon(CouponUsedEvent event);
}
