package kr.co.olivepay.card.repository;

import kr.co.olivepay.card.entity.Card;
import kr.co.olivepay.core.card.dto.req.CardSearchReq;

import java.util.List;

public interface CardCustomRepository {

    List<Card> findByMemberIdAndCardSearchReq(Long memberId, CardSearchReq cardSearchReq);

}
