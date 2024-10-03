package kr.co.olivepay.funding.service;

import kr.co.olivepay.core.transaction.topic.event.coupon.CouponTransferEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferRollBackEvent;
import kr.co.olivepay.funding.dto.res.CouponTransferStateRes;

public interface FundingEventService {

    /**
     * CouponTransferEvent를 통해 공공기부금에서 쿠폰 잔액을 이체합니다.
     *
     * @param event
     * @return 쿠폰 잔액 이체 상태 {@link CouponTransferStateRes}
     */
    CouponTransferStateRes couponTransfer(CouponTransferEvent event);

    /**
     * CouponTransferRollBackEvent를 통해 쿠폰 잔액 이체를 롤백합니다.
     *
     * @param event
     * @return 쿠폰 유저 ID {@link Long}
     */
    Long couponTransferRollBack(CouponTransferRollBackEvent event);

}
