package kr.co.olivepay.payment.service.Impl;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.card.dto.res.enums.CardType;
import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.mapper.PaymentDetailMapper;
import kr.co.olivepay.payment.repository.PaymentDetailRepository;
import kr.co.olivepay.payment.service.PaymentDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentDetailServiceImpl implements PaymentDetailService {

	private static final Long DREAM_TREE_MAX_AMOUNT = 9000L;

	private final PaymentDetailRepository paymentDetailRepository;
	private final PaymentDetailMapper paymentDetailMapper;

	/**
	 * paymentId에 대한 paymentDetail list를 반환합니다.
	 * @param paymentId
	 * @return
	 */
	@Override
	public List<PaymentDetail> getPaymentDetails(Long paymentId) {
		return paymentDetailRepository.findAllByPaymentId(paymentId);
	}

	/**
	 * payment에 대한 paymentDetail을 생성합니다.
	 *
	 *
	 * @param payment
	 * @param remainingAmount
	 * @param couponUnit
	 * @param cardList
	 * @return
	 */
	@Override
	public List<PaymentDetail> createPaymentDetails(Payment payment, Long remainingAmount, Long couponUnit,
		List<PaymentCardSearchRes> cardList) {
		List<PaymentDetail> details = new ArrayList<>();

		List<CardType> paymentOrder = Arrays.asList(CardType.DREAMTREE, CardType.COUPON, CardType.DIFFERENCE);
		Long payingAmount = 0L;
		PaymentDetail detail = null;
		for (CardType cardType : paymentOrder) {
			PaymentCardSearchRes card = cardList.stream()
												 .filter(c -> c.cardType() == cardType)
												 .findFirst()
												 .orElse(null);
			if (card==null) continue;

			if (cardType==CardType.DREAMTREE) {
				payingAmount = Math.min(DREAM_TREE_MAX_AMOUNT, remainingAmount);
			}
			else if (cardType==CardType.COUPON) {
				payingAmount = Math.min(couponUnit, remainingAmount);
			}
			else if (cardType==CardType.DIFFERENCE) {
				payingAmount = remainingAmount;
			}
			if(payingAmount == 0) continue;
			detail = paymentDetailMapper.toEntity(payment, payingAmount, card);
			paymentDetailRepository.save(detail);
			details.add(detail);
			remainingAmount-=payingAmount;
		}

		return details;
	}


}
