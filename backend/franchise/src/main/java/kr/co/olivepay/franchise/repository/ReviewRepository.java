package kr.co.olivepay.franchise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.olivepay.franchise.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

	Long countByFranchiseId(Long franchiseId);

}
