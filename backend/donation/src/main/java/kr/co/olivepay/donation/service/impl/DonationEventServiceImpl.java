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

import java.util.*;

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
            log.info("쿠폰 유저 있음");
            log.info("쿠폰 유저 id{}, 사용 여부{}, 멤버 아이디 {}, 이벤트의 멤버 아이디 {}", couponUser.get().getId(), couponUser.get().getIsUsed()
            ,couponUser.get().getMemberId(), event.memberId());
            if (!couponUser.get()
                           .getIsUsed() && Objects.equals(couponUser.get()
                                                                    .getMemberId(), event.memberId())) {

                log.info("쿠폰 유효성 검증 성공");
                isSuccess = true;
                CouponUser existCouponUser = couponUser.get();
                existCouponUser.updateIsUsed(true);
                couponUserRepository.save(existCouponUser);
                log.info("쿠폰 사용여부 변환 성공");
                sendEmail(existCouponUser);
            }
        }
        log.info("성공여부 :{}", isSuccess);
        return CouponUsedStateRes.builder()
                                 .isSuccess(isSuccess)
                                 .failReason(isSuccess ? null : FAIL_REASON)
                                 .build();
    }

    /**
     * 이메일 전송 메소드
     *
     * @param existCouponUser 이메일 전송에 필요한 정보가 담긴 CouponUser 객체 {@link CouponUser}
     */

    private void sendEmail(CouponUser existCouponUser) {
        try {
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
                                   .histories(Collections.singletonList(history))
                                   .build();
            log.info("이메일 관련 history {}", history);
            log.info("이메일 관련 req {}", req);
            log.info("이메일 전송 관련 dto 생성 완료");
            commonServiceClient.sendEmail(req);
            log.info("이메일 전송 완료");
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("FEIGN CLIENT ERROR : 이메일 전송 에러");
        }
    }
}
