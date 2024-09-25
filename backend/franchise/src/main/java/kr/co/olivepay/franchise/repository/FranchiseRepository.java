package kr.co.olivepay.franchise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.olivepay.franchise.entity.Franchise;
import kr.co.olivepay.franchise.global.enums.ErrorCode;
import kr.co.olivepay.franchise.global.handler.AppException;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Long> {

	Boolean existsByRegistrationNumber(String registrationNumber);

	Optional<Franchise> findByMemberId(Long memberId);

	default Franchise getById(Long id){
		return findById(id).orElseThrow(()->new AppException(ErrorCode.FRANCHISE_NOT_FOUND_BY_ID));
	}
}
