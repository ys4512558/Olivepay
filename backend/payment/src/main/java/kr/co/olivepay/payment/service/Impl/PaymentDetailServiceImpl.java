package kr.co.olivepay.payment.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.payment.dto.req.PaymentCreateReq;
import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.mapper.PaymentDetailMapper;
import kr.co.olivepay.payment.openapi.service.FintechService;
import kr.co.olivepay.payment.repository.PaymentDetailRepository;
import kr.co.olivepay.payment.service.PaymentDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentDetailServiceImpl implements PaymentDetailService {

	private static final Long DREAM_TREE_MAX_AMOUNT = 9000L;

	private final PaymentDetailRepository paymentDetailRepository;
	private final PaymentDetailMapper paymentDetailMapper;
	private final FintechService fintechService;

	@Override
	public List<PaymentDetail> getPaymentDetails(Long paymentId) {
		return paymentDetailRepository.findAllByPaymentId(paymentId);
	}

	@Override
	public List<PaymentDetail> createPaymentDetails(Payment payment, PaymentCreateReq request,
		List<PaymentCardSearchRes> cardList) {
		long remainingAmount = request.amount();
		List<PaymentDetail> details = new ArrayList<>();

		for (PaymentCardSearchRes card : cardList) {
			PaymentDetail detail = switch (card.cardType()) {
				case DREAMTREE -> processDreamTreePayment(payment, card, remainingAmount);
				case COUPON -> processCouponPayment(payment, card, remainingAmount, request.couponUnit());
				case DIFFERENCE -> processDifferencePayment(payment, card, remainingAmount);
			};

			if (detail != null) {
				details.add(detail);
				paymentDetailRepository.save(detail);
				remainingAmount -= detail.getAmount();
			}
		}

		return details;
	}

	private PaymentDetail processDreamTreePayment(Payment payment, PaymentCardSearchRes card, Long amount) {
		long paymentAmount = Math.min(amount, DREAM_TREE_MAX_AMOUNT);
		return paymentDetailMapper.toEntity(payment, paymentAmount, card);
	}

	private PaymentDetail processCouponPayment(Payment payment, PaymentCardSearchRes card, Long amount,
		Long couponUnit) {
		long paymentAmount = Math.min(amount, couponUnit);
		return paymentDetailMapper.toEntity(payment, paymentAmount, card);
	}

	private PaymentDetail processDifferencePayment(Payment payment, PaymentCardSearchRes card, Long amount) {
		return paymentDetailMapper.toEntity(payment, amount, card);
	}


	@Override
	public void processCardPayments(String userKey, List<PaymentDetail> paymentDetails,
		List<PaymentCardSearchRes> cardList, Long franchiseId) {
		Map<Long, PaymentCardSearchRes> cardMap = cardList.stream()
														  .collect(Collectors.toMap(PaymentCardSearchRes::cardId,
															  card -> card));

		paymentDetails
			.forEach(detail -> {
				PaymentCardSearchRes matchingCard = cardMap.get(detail.getPaymentTypeId());
				fintechService.processCardPayment(userKey, matchingCard.cardNumber(), matchingCard.cvc(),
					franchiseId, detail.getAmount());
			});
	}

}
