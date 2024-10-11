package kr.co.olivepay.franchise.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.olivepay.franchise.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

	Long countByFranchiseId(Long franchiseId);

	Boolean existsByMemberIdAndPaymentId(Long memberId, Long paymentId);

	List<Review> findByFranchiseIdOrderByIdDesc(Long franchiseId, PageRequest of);

	List<Review> findByFranchiseIdAndIdLessThanOrderByIdDesc(Long franchiseId, Long lastPaymentId, PageRequest of);

	List<Review> findByMemberIdOrderByIdDesc(Long memberId, PageRequest of);

	List<Review> findByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, Long lastPaymentId, PageRequest of);
}
