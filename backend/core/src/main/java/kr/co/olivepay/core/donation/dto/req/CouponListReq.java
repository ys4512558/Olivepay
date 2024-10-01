package kr.co.olivepay.core.donation.dto.req;

import lombok.Builder;

import java.util.List;

@Builder
public record CouponListReq(
        List<Long> franchiseIdList
) {
}
