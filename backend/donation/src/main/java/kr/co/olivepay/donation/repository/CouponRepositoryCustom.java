package kr.co.olivepay.donation.repository;

import kr.co.olivepay.core.donation.dto.res.CouponRes;

import java.util.List;

public interface CouponRepositoryCustom {

    List<CouponRes> getCouponCountsByFranchiseId(List<Long> franchiseIds);
}
