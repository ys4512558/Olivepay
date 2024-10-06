package kr.co.olivepay.franchise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.olivepay.franchise.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	Long countByFranchiseId(Long FranchiseId);
	Boolean existsByMemberIdAndFranchiseId(Long memberId, Long FranchiseId);

	List<Like> getAllByMemberId(Long memberId);
	void deleteByMemberIdAndFranchiseId(Long memberId, Long franchiseId);

}
