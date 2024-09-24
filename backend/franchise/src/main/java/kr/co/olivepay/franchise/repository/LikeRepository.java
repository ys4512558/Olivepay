package kr.co.olivepay.franchise.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.olivepay.franchise.entity.Franchise;
import kr.co.olivepay.franchise.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

	Integer countByFranchiseId(Long FranchiseId);
	Boolean existsByMemberIdAndFranchiseId(Long memberId, Long FranchiseId);

	List<Like> getAllByMemberId(Long memberId);

}
