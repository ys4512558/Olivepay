package kr.co.olivepay.funding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.olivepay.funding.entity.FundingUsage;

@Repository
public interface FundingUsageRepository extends JpaRepository<FundingUsage, Long> {

	@Query("SELECT SUM(fu.amount) FROM FundingUsage fu")
	Long sumTotalAmount();

}
