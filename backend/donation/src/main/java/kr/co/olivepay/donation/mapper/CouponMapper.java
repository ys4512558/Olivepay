package kr.co.olivepay.donation.mapper;

import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.entity.Coupon;
import kr.co.olivepay.donation.entity.Donation;
import kr.co.olivepay.donation.enums.CouponUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    @Mapping(source = "donationReq.franchiseId", target = "franchiseId")
    @Mapping(
            target = "count",
            expression = "java(couponUnit == CouponUnit.TWO ? donationReq.coupon2() : donationReq.coupon4())"
    )
    Coupon toEntity(Donation donation, CouponUnit couponUnit, DonationReq donationReq);
}
