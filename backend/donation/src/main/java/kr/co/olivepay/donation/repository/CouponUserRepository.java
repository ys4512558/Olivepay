package kr.co.olivepay.donation.repository;

import kr.co.olivepay.donation.entity.CouponUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponUserRepository extends JpaRepository<CouponUser, Long>, CouponUserRepositoryCustom {
    Long countByIsUsed(Boolean isUsed);
}
