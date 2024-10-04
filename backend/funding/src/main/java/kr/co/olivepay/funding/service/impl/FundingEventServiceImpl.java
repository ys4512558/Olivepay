package kr.co.olivepay.funding.service.impl;

import kr.co.olivepay.core.transaction.topic.event.coupon.CouponTransferEvent;
import kr.co.olivepay.core.transaction.topic.event.coupon.result.CouponTransferRollBackEvent;
import kr.co.olivepay.funding.dto.res.CouponTransferStateRes;
import kr.co.olivepay.funding.entity.Funding;
import kr.co.olivepay.funding.global.enums.ErrorCode;
import kr.co.olivepay.funding.global.handler.AppException;
import kr.co.olivepay.funding.global.properties.FundingProperties;
import kr.co.olivepay.funding.mapper.FundingMapper;
import kr.co.olivepay.funding.openapi.service.FintechService;
import kr.co.olivepay.funding.repository.FundingRepository;
import kr.co.olivepay.funding.service.FundingEventService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FundingEventServiceImpl implements FundingEventService {

	private final String CHANGE_INPUT_SUMMARY = "쿠폰 잔액 입금";
	private final String CHANGE_OUTPUT_SUMMARY = "쿠폰 잔액 출금";
	private final String FAILURE_SUFFIX = " 취소";

	private final FintechService fintechService;
	private final FundingProperties fundingProperties;
	private final FundingRepository fundingRepository;
	private final FundingMapper fundingMapper;

	@Override
	public CouponTransferStateRes couponTransfer(CouponTransferEvent event) {

		boolean isSuccess = true;
		String failReason = "";

		validateFunding(event.couponUserId());

		try {

			fintechService.transferAccount(fundingProperties.getUserKey(), fundingProperties.getChangeAccountNo()
				, event.change()
					   .toString(),
				fundingProperties.getDonationAccountNo(),
				CHANGE_INPUT_SUMMARY, CHANGE_OUTPUT_SUMMARY);

		} catch (Exception e) {

			isSuccess = false;
			failReason = "[계좌 이체 실패] 쿠폰 잔액 이체 중 오류가 발생했습니다.";

		}

		if (isSuccess) {
			Funding funding = Funding.builder()
									 .couponUserId(event.couponUserId())
									 .amount(event.change())
									 .build();
			fundingRepository.save(funding);
		}

		return CouponTransferStateRes.builder()
									 .isSuccess(isSuccess)
									 .failReason(failReason)
									 .build();
	}

	private void validateFunding(Long couponMemberId) {
		if (fundingRepository.existsByCouponUserId(couponMemberId)) {
			throw new AppException(ErrorCode.DONATION_DUPLICATED);
		}
	}

	/**
	 *
	 * @param event
	 * @return
	 */
	@Override
	public Long couponTransferRollBack(CouponTransferRollBackEvent event) {
		fundingRepository.deleteByCouponUserId(event.couponUserId());

		fintechService.transferAccount(fundingProperties.getUserKey(), fundingProperties.getDonationAccountNo()
			, event.change()
				   .toString(),
			fundingProperties.getChangeAccountNo(), CHANGE_OUTPUT_SUMMARY+FAILURE_SUFFIX
			, CHANGE_INPUT_SUMMARY+FAILURE_SUFFIX);

		return event.couponUserId();
	}
}
