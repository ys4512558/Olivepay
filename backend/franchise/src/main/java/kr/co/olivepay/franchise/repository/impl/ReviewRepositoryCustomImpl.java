package kr.co.olivepay.franchise.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.olivepay.franchise.repository.ReviewRepositoryCustom;
import lombok.RequiredArgsConstructor;

import static kr.co.olivepay.franchise.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Float getAverageStarsByFranchiseId(Long franchiseId) {
		Double avgStars = queryFactory
			.select(review.stars.avg())
			.from(review)
			.where(review.franchise.id.eq(franchiseId))
			.fetchOne();
		return (avgStars!=null) ? avgStars.floatValue() : 0L;
	}
}
