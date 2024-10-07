package kr.co.olivepay.donation.mapper;

import kr.co.olivepay.donation.dto.res.CouponMyRes;
import kr.co.olivepay.donation.entity.Coupon;
import kr.co.olivepay.donation.entity.CouponUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponUserMapper {

    @Mapping(source = "couponUser.id", target = "couponUserId")
    @Mapping(source = "franchiseId", target = "franchiseId")
    @Mapping(source = "franchiseName", target = "franchiseName")
    @Mapping(source = "couponUser.coupon.couponUnit.value", target = "couponUnit")
    @Mapping(source = "couponUser.coupon.message", target = "message")
    CouponMyRes toCouponMyRes(CouponUser couponUser, Long franchiseId, String franchiseName);

    CouponUser toEntity(Coupon coupon, Long memberId);
}
