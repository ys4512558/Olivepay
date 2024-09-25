package kr.co.olivepay.card.repository;

import kr.co.olivepay.card.entity.Card;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long>, CardRepositoryCustom {

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

    /**
     * 멤버의 모든 카드 목록 조회 꿈나무 카드 우선
     * @param memberId
     * @return
     */
    @EntityGraph(attributePaths = {"cardCompany"})
    List<Card> findByMemberIdOrderByIsDefaultDesc(Long memberId);

    /**
     * 꿈나무 카드 등록 여부 확인을 위한 메서드
     * @param memberId
     * @return
     */
    Optional<Card> findByMemberIdAndIsDefaultTrue(Long memberId);

    /**
     * 카드와 계좌를 함께 반환하는 메서드
     * @param cardId
     * @return 카드 - 계좌
     */
    @EntityGraph(attributePaths = {"account"})
    Optional<Card> findCardAndAccountById(Long cardId);
}
