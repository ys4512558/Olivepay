package kr.co.olivepay.card.repository.Impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.olivepay.card.entity.Card;
import kr.co.olivepay.card.enums.AccountType;
import kr.co.olivepay.card.repository.CardRepositoryCustom;
import kr.co.olivepay.core.card.dto.req.CardSearchReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.co.olivepay.card.entity.QAccount.account;
import static kr.co.olivepay.card.entity.QCard.card;

@Repository
@RequiredArgsConstructor
public class CardRepositoryCustomImpl implements CardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Card> findByMemberIdAndCardSearchReq(Long memberId, CardSearchReq cardSearchReq) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(memberIdEq(memberId));
        booleanBuilder.and(isDefaultTrue().or(cardIdEq(cardSearchReq)));
        booleanBuilder.or(isPublicTrue(cardSearchReq));

        return queryFactory.selectFrom(card)
                           .where(booleanBuilder)
                           .fetch();
    }

    /**
     * isPublic이 true일때 공용 기부금 계좌에 대한 카드를 반환하기 위한 메서드
     *
     * @param cardSearchReq
     * @return
     */
    private BooleanExpression isPublicTrue(CardSearchReq cardSearchReq) {
        if (cardSearchReq == null || cardSearchReq.isPublic() == null || !cardSearchReq.isPublic()) {
            return null;
        }
        Long publicCardId = queryFactory.select(card.id)
                                        .from(card)
                                        .innerJoin(account)
                                        .on(card.account.eq(account))
                                        .where(account.accountType.eq(AccountType.DONATION))
                                        .fetchOne();
        return card.id.eq(publicCardId);
    }

    private BooleanExpression cardIdEq(CardSearchReq cardSearchReq) {
        if (cardSearchReq == null || cardSearchReq.cardId() == null) {
            return null;
        }
        return card.id.eq(cardSearchReq.cardId());
    }

    /**
     * isDefault = true 필터링
     *
     * @return
     */
    private BooleanExpression isDefaultTrue() {
        return card.isDefault.isTrue();
    }

    /**
     * 멤버 ID가 같은지 확인하는 메서드
     *
     * @param memberId
     * @return
     */
    private BooleanExpression memberIdEq(Long memberId) {
        if (memberId == null) {
            return null;
        }
        return card.memberId.eq(memberId);
    }
}
