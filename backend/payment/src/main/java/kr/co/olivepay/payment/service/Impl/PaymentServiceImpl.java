package kr.co.olivepay.payment.service.Impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import kr.co.olivepay.core.transaction.topic.Topic;
import kr.co.olivepay.core.transaction.util.KeyGenerator;
import kr.co.olivepay.payment.dto.res.*;
import kr.co.olivepay.payment.entity.enums.PaymentState;
import kr.co.olivepay.payment.transaction.publisher.TransactionEventPublisher;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.co.olivepay.core.card.dto.req.CardSearchReq;
import kr.co.olivepay.core.card.dto.res.PaymentCardSearchRes;
import kr.co.olivepay.core.card.dto.res.enums.CardType;
import kr.co.olivepay.core.franchise.dto.req.FranchiseIdListReq;
import kr.co.olivepay.core.franchise.dto.res.FranchiseMinimalRes;
import kr.co.olivepay.core.franchise.dto.res.FranchiseMyDonationRes;
import kr.co.olivepay.core.global.dto.res.PageResponse;
import kr.co.olivepay.core.member.dto.req.UserPinCheckReq;
import kr.co.olivepay.core.member.dto.res.UserKeyRes;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentCreateEvent;
import kr.co.olivepay.core.transaction.topic.event.payment.PaymentDetailApplyEvent;
import kr.co.olivepay.payment.client.CardServiceClient;
import kr.co.olivepay.payment.client.FranchiseServiceClient;
import kr.co.olivepay.payment.client.MemberServiceClient;
import kr.co.olivepay.payment.dto.req.PaymentCreateReq;
import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.global.enums.ErrorCode;
import kr.co.olivepay.payment.global.enums.NoneResponse;
import kr.co.olivepay.payment.global.enums.SuccessCode;
import kr.co.olivepay.payment.global.handler.AppException;
import kr.co.olivepay.payment.global.response.Response;
import kr.co.olivepay.payment.global.response.SuccessResponse;
import kr.co.olivepay.payment.mapper.PaymentMapper;
import kr.co.olivepay.payment.openapi.service.FintechService;
import kr.co.olivepay.payment.repository.PaymentDetailRepository;
import kr.co.olivepay.payment.repository.PaymentRepository;
import kr.co.olivepay.payment.service.PaymentDetailService;
import kr.co.olivepay.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private static final Long MERCHANT_ID = 2116L;
	private static final Integer PAGE_SIZE = 20;
	private static final Integer DATE_RANGE = 3;

	private final PaymentRepository paymentRepository;
	private final PaymentMapper paymentMapper;
	private final PaymentDetailService paymentDetailService;

	private final CardServiceClient cardServiceClient;
	private final FranchiseServiceClient franchiseServiceClient;
	private final MemberServiceClient memberServiceClient;
	private final TransactionEventPublisher eventPublisher;

	private final FintechService fintechService;
	private final PaymentDetailRepository paymentDetailRepository;

	@Override
	@Transactional
	public SuccessResponse<PaymentKeyRes> pay(Long memberId, PaymentCreateReq request) {
		//간편결제 비밀번호 검증
		String userKey = validatePaymentPin(request.pin(), memberId);

		//결제 카드 정보 조회
		List<PaymentCardSearchRes> cardList = getPaymentCards(memberId, request);

		log.info("결제 카드 정보 조회 완료");

		//DB에 PENDING 상태로 히스토리 남기기
		Payment payment = createPayment(memberId, request);
		List<PaymentDetail> paymentDetails = paymentDetailService.createPaymentDetails(payment, request.amount(),
				request.couponUnit(), cardList);

		log.info("DB PENDING 저장");

		//이벤트 발행
		PaymentCreateEvent event = paymentMapper.toPaymentCreateEvent(memberId, userKey, request, payment,
				paymentDetails, cardList);
		PaymentKeyRes paymentKeyRes = PaymentKeyRes.builder()
												   .key(KeyGenerator.makeKey())
												   .build();
		publishPaymentPendingEvent(event, paymentKeyRes.key());

		return new SuccessResponse<>(SuccessCode.PAYMENT_REGISTER_SUCCESS, paymentKeyRes);
	}

	private PaymentDetailApplyEvent findEventByCardType(List<PaymentDetailApplyEvent> events, CardType cardType) {
		return events.stream()
					 .filter(e -> e.paymentCard()
								   .cardType() == cardType)
					 .findFirst()
					 .orElse(null);
	}

	/**
	 * member 서비스를 호출하여 간편결제 비밀번호 검증 후 userKey를 반환받습니다.
	 *
	 * @param pin
	 * @param memberId
	 * @return userKey
	 */
	private String validatePaymentPin(String pin, Long memberId) {
		try {
			UserPinCheckReq userPinCheckReq = UserPinCheckReq.builder()
															 .pin(pin)
															 .build();
			UserKeyRes userKeyRes = memberServiceClient.checkUserPin(memberId, userPinCheckReq)
													   .data();
			return userKeyRes.userKey();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * card 서비스를 호출하여 결제 카드 정보를 반환합니다.
	 *
	 * @param memberId
	 * @param request
	 * @return 결제 카드 정보
	 */
	private List<PaymentCardSearchRes> getPaymentCards(Long memberId, PaymentCreateReq request) {
		log.info("결제 카드 정보 받아오기 PaymentCreateReq : {}", request);
		CardSearchReq cardSearchReq = CardSearchReq.builder()
												   .cardId(request.cardId())
												   .isPublic(request.couponUserId() != null)
												   .build();
		log.info("결제 카드 정보 받아오기 CardSearchReq : {}", cardSearchReq);

		List<PaymentCardSearchRes> cards = cardServiceClient.getPaymentCardList(memberId, cardSearchReq)
															.getBody()
															.data();
		log.info("결제 카드 정보 받아오기 cards : {}", cards);

		return cards;
	}

	/**
	 * Payment 히스토리 저장
	 *
	 * @param memberId
	 * @param request
	 * @return
	 */
	private Payment createPayment(Long memberId, PaymentCreateReq request) {
		Payment payment = paymentMapper.toEntity(memberId, request);
		paymentRepository.save(payment);
		return payment;
	}

	/**
	 * 결제 대기 상태 이벤트 발행
	 *
	 * @param event
	 * @param key
	 */
	private void publishPaymentPendingEvent(PaymentCreateEvent event, String key) {
		eventPublisher.publishEvent(
				Topic.PAYMENT_PENDING,
				key,
				event
		);
	}

	/**
	 * 유저 결제 내역 조회
	 *
	 * @param memberId
	 * @param lastPaymentId
	 * @return
	 */
	public SuccessResponse<PageResponse<List<PaymentHistoryFranchiseRes>>> getUserPaymentHistory(Long memberId,
			Long lastPaymentId) {
		List<Payment> payments = fetchUserPayments(memberId, lastPaymentId);
		Map<Long, String> franchiseMap = getFranchiseMap(payments);
		List<PaymentHistoryFranchiseRes> historyResList = mapToPaymentHistoryFranchiseRes(payments, franchiseMap);
		Long nextCursor = payments.isEmpty() ? lastPaymentId : payments.get(payments.size() - 1)
																	   .getId();
		PageResponse<List<PaymentHistoryFranchiseRes>> response = new PageResponse<>(nextCursor, historyResList);
		return new SuccessResponse<>(SuccessCode.USER_PAYMENT_HISTORY_SUCCESS, response);
	}

	private List<Payment> fetchUserPayments(Long memberId, Long lastPaymentId) {
		List<Payment> paymentList = null;
		if (lastPaymentId == null) {
			paymentList = paymentRepository.findByMemberIdAndPaymentStateOrderByIdDesc(memberId, PaymentState.SUCCESS,
					PageRequest.of(0, PAGE_SIZE));
		} else {
			paymentList = paymentRepository.findByMemberIdAndPaymentStateAndIdLessThanOrderByIdDesc(memberId,
					lastPaymentId, PaymentState.SUCCESS,
					PageRequest.of(0, PAGE_SIZE));
		}
		return paymentList;
	}

	/**
	 * franchise 서비스에서 franchise 데이터를 가져옵니다.
	 * 이 데이터로 유저가 어떤 가맹점에서 결제를 했는지 데이터를 넣어줄 수 있게 됩니다.
	 *
	 * @param payments
	 * @return
	 */
	private Map<Long, String> getFranchiseMap(List<Payment> payments) {
		try {
			List<Long> franchiseIds = payments.stream()
											  .map(Payment::getFranchiseId)
											  .distinct()
											  .collect(Collectors.toList());
			FranchiseIdListReq request = FranchiseIdListReq.builder()
														   .franchiseIdList(franchiseIds)
														   .build();
			List<FranchiseMyDonationRes> franchiseList = franchiseServiceClient.getFranchiseListByFranchiseIdList(
																					   request)
																			   .data();
			return franchiseList.stream()
								.collect(
										Collectors.toMap(FranchiseMyDonationRes::franchiseId,
												FranchiseMyDonationRes::name,
												(existing, replacement) -> replacement));
		} catch (Exception e) {
			throw new AppException(ErrorCode.FRANCHISE_FEIGN_CLIENT_ERROR);
		}
	}

	private List<PaymentHistoryFranchiseRes> mapToPaymentHistoryFranchiseRes(List<Payment> payments,
			Map<Long, String> franchiseMap) {
		return payments.stream()
					   .map(payment -> paymentMapper.toPaymentHistoryFranchiseRes(payment,
							   franchiseMap.get(payment.getFranchiseId()),
							   paymentDetailService.getPaymentDetails(payment.getId())))
					   .collect(Collectors.toList());
	}

	/**
	 * 가맹점 거래 내역 조회
	 *
	 * @param memberId
	 * @param franchiseId
	 * @param lastPaymentId
	 * @return
	 */
	@Override
	public SuccessResponse<PageResponse<List<PaymentHistoryRes>>> getFranchisePaymentHistory(Long memberId,
			Long franchiseId, Long lastPaymentId) {
		validateOwnership(memberId, franchiseId);
		List<Payment> payments = fetchFranchisePayments(franchiseId, lastPaymentId);
		List<PaymentHistoryRes> historyResList = mapToPaymentHistoryRes(payments);

		Long nextCursor = payments.isEmpty() ? lastPaymentId : payments.get(payments.size() - 1)
																	   .getId();
		PageResponse<List<PaymentHistoryRes>> response = new PageResponse<>(nextCursor, historyResList);
		return new SuccessResponse<>(SuccessCode.FRANCHISE_PAYMENT_HISTORY_SUCCESS, response);
	}

	/**
	 * 현재 멤버가 가맹점의 소유주인지 확인합니다.
	 *
	 * @param memberId
	 * @param franchiseId
	 */
	private void validateOwnership(Long memberId, Long franchiseId) {
		try {
			Response<FranchiseMinimalRes> response = franchiseServiceClient.getFranchiseByMemberId(memberId);
			FranchiseMinimalRes franchiseData = response.data();
			if (franchiseData == null || !Objects.equals(franchiseData.id(), franchiseId)) {
				throw new AppException(ErrorCode.OWNERSHIP_REQUIRED);
			}
		} catch (Exception e) {
			throw new AppException(ErrorCode.FRANCHISE_FEIGN_CLIENT_ERROR);
		}
	}

	private List<Payment> fetchFranchisePayments(Long franchiseId, Long lastPaymentId) {
		List<Payment> paymentList = null;
		if (lastPaymentId == null) {
			paymentList = paymentRepository.findByFranchiseIdAndPaymentStateOrderByIdDesc(franchiseId,
					PaymentState.SUCCESS, PageRequest.of(0, PAGE_SIZE));
		} else {
			paymentList = paymentRepository.findByFranchiseIdAndPaymentStateAndIdLessThanOrderByIdDesc(franchiseId,
					lastPaymentId, PaymentState.SUCCESS,
					PageRequest.of(0, PAGE_SIZE));
		}
		return paymentList;
	}

	private List<PaymentHistoryRes> mapToPaymentHistoryRes(List<Payment> payments) {
		return payments.stream()
					   .map(payment -> paymentMapper.toPaymentHistoryRes(payment,
							   paymentDetailService.getPaymentDetails(payment.getId())))
					   .collect(Collectors.toList());
	}

	/**
	 * 유저의 최근 3일 내 결제내역을 조회합니다.
	 *
	 * @param memberId
	 * @return
	 */
	@Override
	public SuccessResponse<List<PaymentMinimalRes>> getRecentPaymentIds(Long memberId) {
		LocalDateTime threeDaysAgo = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
												  .minusDays(DATE_RANGE)
												  .truncatedTo(ChronoUnit.DAYS)
												  .toLocalDateTime();
		List<Payment> paymentList = paymentRepository.findRecentSuccessfulPaymentsByMemberId(memberId, threeDaysAgo);

		List<PaymentMinimalRes> response = paymentMapper.toPaymentMinimalResList(paymentList);
		return new SuccessResponse<>(SuccessCode.RECENT_PAYMENT_SEARCH_SUCCESS, response);
	}
}