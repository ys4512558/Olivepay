package kr.co.olivepay.payment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentCreateEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailCreateEvent;
import kr.co.olivepay.payment.dto.req.PaymentCreateReq;
import kr.co.olivepay.payment.dto.res.PaymentHistoryFranchiseRes;
import kr.co.olivepay.payment.dto.res.PaymentHistoryRes;
import kr.co.olivepay.payment.dto.res.PaymentMinimalRes;
import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.entity.enums.PaymentState;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "memberId", source = "memberId")
	@Mapping(target = "franchiseId", source = "request.franchiseId")
	@Mapping(target = "paymentState", expression = "java(initPaymentState())")
	@Mapping(target = "failureReason", expression = "java(null)")
	Payment toEntity(Long memberId, PaymentCreateReq request);

	@Mapping(source = "payment.id", target = "paymentId")
	@Mapping(source = "paymentDetailList", target = "amount", qualifiedByName = "calculateTotalAmount")
	@Mapping(source = "paymentDetailList", target = "details")
	PaymentHistoryRes toPaymentHistoryRes(Payment payment, List<PaymentDetail> paymentDetailList);

	@Mapping(source = "payment.id", target = "paymentId")
	@Mapping(source = "paymentDetailList", target = "amount", qualifiedByName = "calculateTotalAmount")
	@Mapping(source = "paymentDetailList", target = "details")
	PaymentHistoryFranchiseRes toPaymentHistoryFranchiseRes(Payment payment, String franchiseName, List<PaymentDetail> paymentDetailList);

	@Mapping(source = "id", target = "paymentId")
	PaymentMinimalRes toPaymentMinimalRes(Payment payment);

	List<PaymentMinimalRes> toPaymentMinimalResList(List<Payment> paymentList);

	default PaymentCreateEvent toPaymentCreateEvent(Long memberId, String userKey, PaymentCreateReq request,
		Payment payment, List<PaymentDetail> paymentDetails,
		List<PaymentCardSearchRes> cardList) {
		List<PaymentDetailCreateEvent> detailEvents = paymentDetails.stream()
																	.map(detail -> toPaymentDetailCreateEvent(detail, cardList))
																	.collect(Collectors.toList());

		return PaymentCreateEvent.builder()
								 .paymentId(payment.getId())
								 .memberId(memberId)
								 .userKey(userKey)
								 .franchiseId(request.franchiseId())
								 .couponUserId(request.couponUserId())
								 .couponUnit(request.couponUnit())
								 .paymentDetailCreateEventList(detailEvents)
								 .build();
	}

	default PaymentDetailCreateEvent toPaymentDetailCreateEvent(PaymentDetail detail,
		List<PaymentCardSearchRes> cardList) {
		PaymentCardSearchRes matchingCard = cardList.stream()
													.filter(card -> card.cardId().equals(detail.getPaymentTypeId()))
													.findFirst()
													.orElse(null);

		return PaymentDetailCreateEvent.builder()
									   .paymentDetailId(detail.getId())
									   .price(detail.getAmount())
									   .paymentCard(matchingCard)
									   .build();
	}

	@Named("initPaymentState")
	default PaymentState initPaymentState() {
		return PaymentState.PENDING;
	}

	@Named("calculateTotalAmount")
	default Long calculateTotalAmount(List<PaymentDetail> paymentDetails) {
		if (paymentDetails == null || paymentDetails.isEmpty()) {
			return 0L;
		}
		return paymentDetails.stream()
							 .mapToLong(PaymentDetail::getAmount)
							 .sum();
	}


}
