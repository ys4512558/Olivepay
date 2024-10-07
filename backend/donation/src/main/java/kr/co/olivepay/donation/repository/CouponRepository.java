package kr.co.olivepay.donation.repository;

import kr.co.olivepay.donation.entity.Coupon;
import kr.co.olivepay.donation.enums.CouponUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {
    Optional<Coupon> findCouponByCouponUnitAndFranchiseId(CouponUnit couponUnit, Long franchiseId);
}
