package kr.co.olivepay.payment.service.Impl;

import kr.co.olivepay.core.card.dto.res.enums.CardType;
import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentCompleteEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentFailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackDetailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackEvent;
import kr.co.olivepay.payment.client.FundingServiceClient;
import kr.co.olivepay.payment.dto.res.PaymentApplyStateRes;
import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.entity.enums.PaymentState;
import kr.co.olivepay.payment.openapi.dto.res.card.rec.CreateCardTransactionRec;
import kr.co.olivepay.payment.openapi.service.FintechService;
import kr.co.olivepay.payment.repository.PaymentDetailRepository;
import kr.co.olivepay.payment.repository.PaymentRepository;
import kr.co.olivepay.payment.service.PaymentEventService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PaymentEventServiceImpl implements PaymentEventService {

	private static final Long MERCHANT_ID = 2116L;

	private final FintechService fintechService;
	private final PaymentRepository paymentRepository;
	private final PaymentDetailRepository paymentDetailRepository;
	private final FundingServiceClient fundingServiceClient;


	/**
	 * 결제 적용 더미 서비스 로직
	 *
	 * @param event
	 * @return
	 */
	@Override
	public PaymentApplyStateRes paymentApply(PaymentApplyEvent event) {
		List<PaymentApplyHistory> history = new ArrayList<>();
		boolean isSuccess = true;
		String failReason = null;
		Long couponChange = 0L;

		// 결제 순서를 정의합니다.
		List<CardType> paymentOrder = Arrays.asList(CardType.DREAMTREE, CardType.COUPON, CardType.DIFFERENCE);
		for (CardType cardType : paymentOrder) {

			//CardType에 해당하는 applyEvent를 필터링합니다.
			PaymentDetailApplyEvent applyEvent = findEventByCardType(event.paymentDetailApplyEventList(), cardType);
			if (applyEvent == null)
				continue;

			CreateCardTransactionRec rec = null;
			try {
				//카드 결제 시도
				rec = fintechService.processCardPayment(event.userKey(),
					applyEvent.paymentCard()
							  .cardNumber(),
					applyEvent.paymentCard()
							  .cvc(),
					MERCHANT_ID,
					applyEvent.price());
			} catch (Exception e) {
				//실패 시 결제를 중단합니다.
				isSuccess = false;
				failReason = "[카드 결제 실패] 타입: " + cardType + " 카드 넘버: " + applyEvent.paymentCard()
																	 .cardNumber();
				break;
			}

			//쿠폰 결제가 성공적으로 이뤄진 경우 쿠폰 잔액을 계산한다.
			if (cardType == CardType.COUPON) {
				//TODO: couponUnit, couponUserId 필요
				//couponChange = event.couponUnit() - applyEvent.price();
			}

			PaymentApplyHistory paymentApplyHistory = PaymentApplyHistory.builder()
																		 .paymentDetailId(
																			 applyEvent.paymentDetailId())
																		 .transactionUniqueNo(
																			 rec.getTransactionUniqueNo())
																		 .build();
			history.add(paymentApplyHistory);
		}

		//모든 결제가 성공적으로 이뤄졌고, 쿠폰 잔돈이 양수라면
		if ( isSuccess && couponChange > 0L){
			//쿠폰 잔액 이체
			//processCouponChange(couponUserId, couponChange);
		}

		return PaymentApplyStateRes.builder()
								   .isSuccess(isSuccess)
								   .failReason(failReason)
								   .paymentApplyHistoryList(history)
								   .build();
	}


	private PaymentDetailApplyEvent findEventByCardType(List<PaymentDetailApplyEvent> events, CardType cardType) {
		return events.stream()
					 .filter(e -> e.paymentCard()
								   .cardType() == cardType)
					 .findFirst()
					 .orElse(null);
	}

	/**
	 * 결제 롤백 더미 서비스 로직
	 *
	 * @param event
	 * @return
	 */
	@Override
	public Long paymentRollBack(PaymentRollBackEvent event) {

		//이전에 성공했던 카드 결제를 취소합니다.
		for (PaymentRollBackDetailEvent detailEvent : event.paymentRollBackDetailEventList()) {
			fintechService.cancelCardPayment(event.userKey(), detailEvent.paymentCard()
																		 .cardNumber(), detailEvent.paymentCard()
																								   .cvc(),
				Long.valueOf(detailEvent.transactionUniqueNo()));
		}

		return event.paymentId();
	}

	/**
	 * 결제 종료 : 결제 상태 완료로 변경
	 *
	 * @param event
	 */
	@Override
	public void paymentComplete(PaymentCompleteEvent event) {

		paymentRepository.updatePaymentState(event.paymentId(), PaymentState.SUCCESS);
		List<PaymentDetail> details = paymentDetailRepository.findAllByPaymentId(event.paymentId());
		for (PaymentDetail detail : details) {
			paymentDetailRepository.updatePaymentDetailState(detail.getId(), PaymentState.SUCCESS);
		}

	}

	/**
	 * 결제 종료 : 결제 상태 실패로 변경
	 *
	 * @param event
	 */
	@Override
	public void paymentFail(PaymentFailEvent event) {

		paymentRepository.updatePaymentState(event.paymentId(), PaymentState.FAILURE);
		List<PaymentDetail> details = paymentDetailRepository.findAllByPaymentId(event.paymentId());
		for (PaymentDetail detail : details) {
			paymentDetailRepository.updatePaymentDetailState(detail.getId(), PaymentState.FAILURE);
		}

	}
}
