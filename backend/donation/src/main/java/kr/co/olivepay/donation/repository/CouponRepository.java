package kr.co.olivepay.donation.repository;

import kr.co.olivepay.donation.entity.Coupon;
import kr.co.olivepay.donation.enums.CouponUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {
    List<Coupon> findAllByCouponUnitAndFranchiseId(CouponUnit couponUnit, Long franchiseId);
}
