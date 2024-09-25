package kr.co.olivepay.franchise.repository;

import java.util.List;

import kr.co.olivepay.franchise.entity.Review;

public interface ReviewRepositoryCustom {
	List<Review> findAllByMemberIdAfterIndex(Long memberId, Long index);
	List<Review> findAllByFranchiseIdAfterIndex(Long franchiseId, Long index);
}
