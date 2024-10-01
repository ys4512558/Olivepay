package kr.co.olivepay.funding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.olivepay.funding.entity.Funding;

@Repository
public interface FundingRepository extends JpaRepository<Funding, Long> {

	@Query("SELECT SUM(f.amount) FROM Funding f")
	Long sumTotalAmount();

	Boolean existsByCouponUserId(Long couponUserId);

}
