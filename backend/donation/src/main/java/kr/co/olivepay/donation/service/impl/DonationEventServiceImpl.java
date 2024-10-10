package kr.co.olivepay.donation.service.impl;

import kr.co.olivepay.core.common.dto.req.CouponUseHistory;
import kr.co.olivepay.core.common.dto.req.EmailReq;
import kr.co.olivepay.core.transaction.topic.event.coupon.CouponUsedEvent;
import kr.co.olivepay.donation.client.CommonServiceClient;
import kr.co.olivepay.donation.dto.res.CouponUsedStateRes;
import kr.co.olivepay.donation.entity.CouponUser;
import kr.co.olivepay.donation.repository.CouponUserRepository;
import kr.co.olivepay.donation.service.DonationEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationEventServiceImpl implements DonationEventService {
    private final CouponUserRepository couponUserRepository;
    private final CommonServiceClient commonServiceClient;
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
                sendEmail(existCouponUser);
            }
        }
        return CouponUsedStateRes.builder()
                                 .isSuccess(isSuccess)
                                 .failReason(isSuccess ? null : FAIL_REASON)
                                 .build();
    }

    /**
     * 이메일 전송 메소드
     * @param existCouponUser 이메일 전송에 필요한 정보가 담긴 CouponUser 객체 {@link CouponUser}
     */

    private void sendEmail(CouponUser existCouponUser) {
        String donorEmail = existCouponUser.getCoupon()
                                           .getDonation()
                                           .getDonor()
                                           .getEmail();
        CouponUseHistory history = CouponUseHistory.builder()
                                                   .couponUnit(existCouponUser.getCoupon()
                                                                              .getCouponUnit()
                                                                              .getValue()
                                                                              .toString())
                                                   .date(new Date())
                                                   .build();
        EmailReq req = EmailReq.builder()
                               .email(donorEmail)
                               .histories(List.of(history))
                               .build();

        try {
            commonServiceClient.sendEmail(req);
        } catch (Exception e) {
            log.error("FEIGN CLIENT ERROR : 이메일 전송 에러");
        }
    }
}
