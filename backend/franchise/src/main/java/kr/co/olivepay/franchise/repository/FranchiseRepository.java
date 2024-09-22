package kr.co.olivepay.franchise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.olivepay.franchise.entity.Franchise;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Long> {

	Boolean existsByRegistrationNumber(String registrationNumber);
}
