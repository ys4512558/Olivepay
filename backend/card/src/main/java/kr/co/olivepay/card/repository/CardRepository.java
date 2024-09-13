package kr.co.olivepay.card.repository;

import kr.co.olivepay.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * 실제 카드 번호를 통해 카드를 조회하는 쿼리메서드
     */
    Optional<Card> findByRealCardNumber(String realCardNumber);
}
