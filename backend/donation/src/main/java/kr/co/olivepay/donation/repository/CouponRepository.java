package kr.co.olivepay.donation.repository;

import kr.co.olivepay.donation.entity.Coupon;
import kr.co.olivepay.donation.enums.CouponUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {
    List<Coupon> findAllByCouponUnitAndFranchiseId(CouponUnit couponUnit, Long franchiseId);

    @Modifying
    @Query("UPDATE Coupon c SET c.count = c.count - 1 WHERE c.id = :couponId AND c.version = :version AND c.count > 0")
    int decreaseCouponCount(@Param("couponId") Long couponId, @Param("version") Long version);
}
