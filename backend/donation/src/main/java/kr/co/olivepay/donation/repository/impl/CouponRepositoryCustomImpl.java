package kr.co.olivepay.donation.repository.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.olivepay.core.donation.dto.res.CouponRes;
import kr.co.olivepay.donation.enums.CouponUnit;
import kr.co.olivepay.donation.repository.CouponRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.co.olivepay.donation.entity.QCoupon.coupon;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CouponRes> getCouponCountsByFranchiseId(List<Long> franchiseIds) {
        // franchiseId 별로 2000,4000 쿠폰 갯수 세기
        List<CouponRes> resultList = queryFactory
                .select(Projections.constructor(
                        CouponRes.class,
                        coupon.franchiseId,
                        sumCouponUnit(CouponUnit.TWO, "coupon2"),
                        sumCouponUnit(CouponUnit.FOUR, "coupon4")
                ))
                .from(coupon)
                .where(coupon.franchiseId.in(franchiseIds))
                .groupBy(coupon.franchiseId)
                .fetch();

        // 쿠폰이 아예 없는 franchiseId에 대해 0, 0으로 초기화된 CouponRes 추가
        List<CouponRes> missingFranchiseResults = franchiseIds.stream()
                                                              .filter(franchiseId -> resultList.stream()
                                                                                               .noneMatch(res -> res.franchiseId()
                                                                                                                    .equals(franchiseId)))
                                                              .map(franchiseId -> CouponRes.builder()
                                                                                           .franchiseId(franchiseId)
                                                                                           .coupon2(0L)  // COUPON2를 0으로 설정
                                                                                           .coupon4(0L)  // COUPON4를 0으로 설정
                                                                                           .build())
                                                              .toList();
        resultList.addAll(missingFranchiseResults);

        return resultList;
    }

    // 쿠폰 유닛별로 합친 갯수를 알맞는 컬럼 이름으로 리턴
    private Expression<Long> sumCouponUnit(CouponUnit couponUnit, String alias) {
        return ExpressionUtils.as(
                new CaseBuilder()
                        .when(coupon.couponUnit.eq(couponUnit))
                        .then(coupon.count)
                        .otherwise(0L)
                        .sum(),
                alias
        );
    }
}
