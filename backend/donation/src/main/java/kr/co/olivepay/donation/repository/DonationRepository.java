package kr.co.olivepay.donation.repository;

import kr.co.olivepay.donation.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
