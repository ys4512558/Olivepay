package kr.co.olivepay.franchise.repository.impl;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.olivepay.franchise.entity.QReview;
import kr.co.olivepay.franchise.entity.Review;
import kr.co.olivepay.franchise.repository.ReviewRepositoryCustom;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Review> findAllByMemberIdAfterIndex(Long memberId, Long index) {
		QReview review = QReview.review;
		return queryFactory.selectFrom(review)
						   .where(review.memberId.eq(memberId)
												 .and(review.id.gt(index)))
						   .orderBy(review.createdAt.asc())
						   .limit(20)
						   .fetch();
	}

	@Override
	public List<Review> findAllByFranchiseIdAfterIndex(Long franchiseId, Long index) {
		QReview review = QReview.review;
		return queryFactory.selectFrom(review)
						   .where(review.franchise.id.eq(franchiseId)
													 .and(review.id.gt(index)))
						   .orderBy(review.createdAt.asc())
						   .limit(20)
						   .fetch();
	}

	@Override
	public Float getAverageStarsByFranchiseId(Long franchiseId) {
		QReview review = QReview.review;
		Double avgStars = queryFactory
			.select(review.stars.avg())
			.from(review)
			.where(review.franchise.id.eq(franchiseId))
			.fetchOne();
		return avgStars.floatValue();
	}
}
