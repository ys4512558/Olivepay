package kr.co.olivepay.donation.service.impl;

import kr.co.olivepay.donation.dto.res.CouponUsedStateRes;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponUsedEvent;
import kr.co.olivepay.donation.entity.CouponUser;
import kr.co.olivepay.donation.repository.CouponUserRepository;
import kr.co.olivepay.donation.service.DonationEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationEventServiceImpl implements DonationEventService {
    private final CouponUserRepository couponUserRepository;
    private final String FAIL_REASON = "쿠폰이 유효하지 않습니다.";
    @Override
    public CouponUsedStateRes useCoupon(CouponUsedEvent event) {
        Boolean isSuccess = false;
        Optional<CouponUser> couponUser = couponUserRepository.findCouponUserById(event.couponUserId());
        if (couponUser.isPresent()) {
            if(!couponUser.get().getIsUsed() && Objects.equals(couponUser.get().getMemberId(), event.memberId())){
                isSuccess = true;
                CouponUser existCouponUser = couponUser.get();
                existCouponUser.updateIsUsed(true);
                couponUserRepository.save(existCouponUser);
            }
        }
        return CouponUsedStateRes.builder()
                                 .isSuccess(isSuccess)
                                 .failReason(isSuccess ? null : FAIL_REASON)
                                 .build();
    }
}
