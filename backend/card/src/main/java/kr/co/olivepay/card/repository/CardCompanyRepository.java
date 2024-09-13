package kr.co.olivepay.card.repository;

import kr.co.olivepay.card.entity.CardCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardCompanyRepository extends JpaRepository<CardCompany, Long> {

    Optional<CardCompany> findByName(String name);

}
