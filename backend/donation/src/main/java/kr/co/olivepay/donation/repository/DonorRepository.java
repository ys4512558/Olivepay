package kr.co.olivepay.donation.repository;

import kr.co.olivepay.donation.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    Optional<Donor> findByPhoneNumber(final String phoneNumber);

    Optional<Donor> findByEmailAndPhoneNumber(final String email, final String phoneNumber);
}
