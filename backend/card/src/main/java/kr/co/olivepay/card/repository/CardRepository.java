package kr.co.olivepay.card.repository;

import kr.co.olivepay.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * 실제 카드 번호를 통해 카드를 조회하는 쿼리메서드
     */
    Optional<Card> findByRealCardNumber(String realCardNumber);

    /**
     * 카드 삭제 쿼리메서드
     * @param id
     * @param memberId
     * @return 삭제된 레코드 개수
     */
    int deleteByIdAndMemberId(Long id, Long memberId);
}
