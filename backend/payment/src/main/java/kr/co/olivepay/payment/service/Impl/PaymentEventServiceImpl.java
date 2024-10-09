package kr.co.olivepay.payment.service.Impl;

import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.card.dto.res.enums.CardType;
import kr.co.olivepay.core.payment.dto.res.PaymentApplyHistory;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailApplyEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentCompleteEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentFailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackDetailEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.result.PaymentRollBackEvent;
import kr.co.olivepay.payment.dto.res.PaymentApplyStateRes;
import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.entity.enums.PaymentState;
import kr.co.olivepay.payment.global.properties.FintechProperties;
import kr.co.olivepay.payment.openapi.dto.res.card.rec.CreateCardTransactionRec;
import kr.co.olivepay.payment.openapi.service.FintechService;
import kr.co.olivepay.payment.repository.PaymentDetailRepository;
import kr.co.olivepay.payment.repository.PaymentRepository;
import kr.co.olivepay.payment.service.PaymentEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventServiceImpl implements PaymentEventService {

	private static final Long MERCHANT_ID = 2116L;

	private final FintechService fintechService;
	private final PaymentRepository paymentRepository;
	private final PaymentDetailRepository paymentDetailRepository;

	private final FintechProperties fintechProperties;

	/**
	 * 결제 적용 서비스 로직
	 *
	 * @param event
	 * @return
	 */
	@Override
	public PaymentApplyStateRes paymentApply(PaymentApplyEvent event) {
		List<PaymentApplyHistory> history = new ArrayList<>();
		boolean isSuccess = true;
		String failReason = null;

		// 결제 순서를 정의합니다.
		List<CardType> paymentOrder = Arrays.asList(CardType.DREAMTREE, CardType.COUPON, CardType.DIFFERENCE);
		for (CardType cardType : paymentOrder) {

			//CardType에 해당하는 applyEvent를 필터링합니다.
			PaymentDetailApplyEvent applyEvent = findEventByCardType(event.paymentDetailApplyEventList(), cardType);
			if (applyEvent == null)
				continue;


			log.info("쿠폰 결제 시도 : Event : {}", event);
			CreateCardTransactionRec rec = null;
			try {
				//카드 결제 시도
				if (cardType==CardType.COUPON){
					log.info("쿠폰 결제 시도 : {}", fintechProperties.getOliveUserKey());
					rec = fintechService.processCardPayment(fintechProperties.getOliveUserKey(),
							applyEvent.paymentCard()
									  .cardNumber(),
							applyEvent.paymentCard()
									  .cvc(),
							MERCHANT_ID,
							applyEvent.price());
				}
				else {
					rec = fintechService.processCardPayment(event.userKey(),
							applyEvent.paymentCard()
									  .cardNumber(),
							applyEvent.paymentCard()
									  .cvc(),
							MERCHANT_ID,
							applyEvent.price());
				}


			} catch (Exception e) {
				//실패 시 결제를 중단합니다.
				isSuccess = false;
				failReason = "[카드 결제 실패] 타입: " + cardType + " 카드 넘버: " + applyEvent.paymentCard()
																				   .cardNumber();
				break;
			}

			PaymentApplyHistory paymentApplyHistory = PaymentApplyHistory.builder()
																		 .paymentDetailId(
																			 applyEvent.paymentDetailId())
																		 .transactionUniqueNo(
																			 rec.getTransactionUniqueNo())
																		 .build();
			history.add(paymentApplyHistory);
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
	 * 결제 롤백 서비스 로직
	 *
	 * @param event
	 * @return
	 */
	@Override
	public Long paymentRollBack(PaymentRollBackEvent event) {
		//이전에 성공했던 카드 결제를 취소합니다.
		List<PaymentRollBackDetailEvent> paymentRollBackDetailEvents = event.paymentRollBackDetailEventList();
		for (PaymentRollBackDetailEvent detailEvent : paymentRollBackDetailEvents) {
			//transactionUniqueNo이 null이 아닌 결제에 대해서만 결제 취소)
			log.info("롤백 수행 : [{}]", detailEvent);
			log.info("롤백 수행 : [trasnactionUnieuqNo : {}]", detailEvent.transactionUniqueNo());
			if (detailEvent.transactionUniqueNo() != null) {
				PaymentCardSearchRes paymentCardSearchRes = detailEvent.paymentCard();
				//결제 취소 요청
				log.info("핀테크 결제 취소 요청");
				fintechService.cancelCardPayment(
						event.userKey(),
						paymentCardSearchRes.cardNumber(),
						paymentCardSearchRes.cvc(),
						Long.valueOf(detailEvent.transactionUniqueNo())
				);
				log.info("롤백 완료 : [{}]", detailEvent);
			}
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
		Payment payment = paymentRepository.getById(event.paymentId());
		payment.updatePaymentState(PaymentState.SUCCESS);
		paymentRepository.save(payment);

		List<PaymentDetail> details = paymentDetailRepository.findAllByPaymentId(event.paymentId());
		for (PaymentDetail detail : details) {
			detail.updatePaymentDetailState(PaymentState.SUCCESS);
			paymentDetailRepository.save(detail);
		}
	}

	/**
	 * 결제 종료 : 결제 상태 실패로 변경
	 *
	 * @param event
	 */
	@Override
	public void paymentFail(PaymentFailEvent event) {
		Payment payment = paymentRepository.getById(event.paymentId());
		payment.updatePaymentState(PaymentState.FAILURE);
		paymentRepository.save(payment);

		List<PaymentDetail> details = paymentDetailRepository.findAllByPaymentId(event.paymentId());
		for (PaymentDetail detail : details) {
			detail.updatePaymentDetailState(PaymentState.FAILURE);
			paymentDetailRepository.save(detail);
		}
	}
}
