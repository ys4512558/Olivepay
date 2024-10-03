package kr.co.olivepay.funding.service.impl;

import kr.co.olivepay.core.transaction.topic.event.coupon.CouponTransferEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferRollBackEvent;
import kr.co.olivepay.funding.dto.res.CouponTransferStateRes;
import kr.co.olivepay.funding.service.FundingEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FundingEventServiceImpl implements FundingEventService {
    @Override
    public CouponTransferStateRes couponTransfer(CouponTransferEvent event) {
        //성공
        CouponTransferStateRes success = CouponTransferStateRes.builder()
                                                               .isSuccess(true)
                                                               .build();
        //실패
        CouponTransferStateRes fail = CouponTransferStateRes.builder()
                                                            .isSuccess(false)
                                                            .failReason("실패했움")
                                                            .build();
        return success;
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    public Long couponTransferRollBack(CouponTransferRollBackEvent event) {
        Long couponUserId = 1L;
        return couponUserId;
    }
}
