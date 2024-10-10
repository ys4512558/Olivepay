package kr.co.olivepay.donation.repository;

import kr.co.olivepay.donation.entity.CouponUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponUserRepository extends JpaRepository<CouponUser, Long>, CouponUserRepositoryCustom {
    Long countByIsUsed(Boolean isUsed);

    Optional<CouponUser> findCouponUserById(Long couponUserId);

    Long countByMemberIdAndIsUsed(Long memberId, Boolean isUsed);
}
