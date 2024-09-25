package kr.co.olivepay.franchise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.olivepay.franchise.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Long countByFranchiseId(Long franchiseId);

}
