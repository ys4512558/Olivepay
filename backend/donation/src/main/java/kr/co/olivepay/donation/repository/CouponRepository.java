package kr.co.olivepay.donation.repository;

import kr.co.olivepay.donation.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {

}
