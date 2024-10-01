package kr.co.olivepay.donation.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.olivepay.donation.entity.CouponUser;
import kr.co.olivepay.donation.repository.CouponUserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.co.olivepay.donation.entity.QCoupon.coupon;
import static kr.co.olivepay.donation.entity.QCouponUser.couponUser;

@Repository
@RequiredArgsConstructor
public class CouponUserRepositoryCustomImpl implements CouponUserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CouponUser> findCouponsByMemberIdAndUnused(Long memberId, Long franchiseId) {

        // 멤버 아이디가 일치하면서 사용하지 않은 쿠폰-유저 객체 반환
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(couponUser.memberId.eq(memberId))
                   .and(couponUser.isUsed.eq(false));

        // 가맹점 아이디가 같이 전달된 경우 조건에 추가
        if (franchiseId != null) {
            whereClause.and(coupon.franchiseId.eq(franchiseId));
        }

        return queryFactory
                .selectFrom(couponUser)
                .join(couponUser.coupon, coupon)
                .where(whereClause)
                .fetch();
    }
}
