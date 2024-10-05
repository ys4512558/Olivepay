package kr.co.olivepay.payment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.card.dto.res.enums.CardType;
import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.entity.enums.PaymentType;

@Mapper(componentModel = "spring")
public interface PaymentDetailMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "paymentDetailState", constant = "PENDING")
	@Mapping(source = "card.cardType", target = "paymentType", qualifiedByName = "cardTypeToPaymentType")
	@Mapping(source = "card.cardId", target = "paymentTypeId")
	@Mapping(source = "payment", target = "payment")
	PaymentDetail toEntity(Payment payment, Long amount, PaymentCardSearchRes card);

	@Named("cardTypeToPaymentType")
	default PaymentType cardTypeToPaymentType(CardType cardType) {
		if (cardType == null) {
			throw new IllegalArgumentException("CardType cannot be null");
		}
		return cardType == CardType.COUPON ? PaymentType.COUPON : PaymentType.CARD;
	}
}
