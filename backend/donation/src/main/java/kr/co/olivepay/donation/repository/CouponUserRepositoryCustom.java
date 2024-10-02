package kr.co.olivepay.donation.repository;

import kr.co.olivepay.donation.entity.CouponUser;

import java.util.List;

public interface CouponUserRepositoryCustom {
    List<CouponUser> findCouponsByMemberIdAndUnused(Long memberId, Long franchiseId);
}
