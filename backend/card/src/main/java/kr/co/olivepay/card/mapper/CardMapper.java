package kr.co.olivepay.card.mapper;

import kr.co.olivepay.card.dto.req.CardRegisterReq;
import kr.co.olivepay.card.dto.res.MyCardSearchRes;
import kr.co.olivepay.card.entity.Account;
import kr.co.olivepay.card.entity.Card;
import kr.co.olivepay.card.entity.CardCompany;
import kr.co.olivepay.card.openapi.dto.res.card.rec.CardRec;
import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.card.dto.res.enums.CardType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CardMapper {

    String DREAM_TREE_CARD = "꿈나무카드";
    String COUPON_CARD = "쿠폰카드";

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "memberId", target = "memberId")
    @Mapping(source = "cardRec.cardNo", target = "cardNumber")
    @Mapping(source = "cardRegisterReq.realCardNumber", target = "realCardNumber")
    @Mapping(source = "cardRegisterReq.expirationYear", target = "expirationYear")
    @Mapping(source = "cardRegisterReq.expirationMonth", target = "expirationMonth")
    @Mapping(source = "cardRec.cvc", target = "cvc")
    @Mapping(source = "cardRegisterReq.creditPassword", target = "creditPassword")
    @Mapping(source = "cardRec.cardName", target = "isDefault", qualifiedByName = "mapIsDefault")
    @Mapping(source = "cardRec.cardName", target = "cardType", qualifiedByName = "mapCardType")
    Card toEntity(Long memberId, Account account, CardRec cardRec, CardRegisterReq cardRegisterReq, CardCompany cardCompany);

    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "card.cardCompany.name", target = "cardCompany")
    MyCardSearchRes toMyCardSearchRes(Card card);

    @Mapping(source = "card.id", target = "cardId")
    PaymentCardSearchRes toPaymentCardSearchRes(Card card);

    @Named("mapIsDefault")
    default Boolean mapIsDefault(String cardName) {
        return cardName.equals("꿈나무카드");
    }

    @Named("mapCardType")
    default CardType mapCardType(String cardName) {
        switch (cardName) {
            case DREAM_TREE_CARD:
                return CardType.DREAMTREE;
            case COUPON_CARD:
                return CardType.COUPON;
            default:
                return CardType.DIFFERENCE;
        }
    }
}
