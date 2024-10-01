package kr.co.olivepay.funding.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.olivepay.funding.dto.req.FundingCreateReq;
import kr.co.olivepay.funding.dto.req.FundingUsageCreateReq;
import kr.co.olivepay.funding.dto.res.FundingAmountRes;
import kr.co.olivepay.funding.dto.res.FundingUsageRes;
import kr.co.olivepay.funding.entity.Funding;
import kr.co.olivepay.funding.entity.FundingUsage;
import kr.co.olivepay.funding.global.enums.ErrorCode;
import kr.co.olivepay.funding.global.enums.NoneResponse;
import kr.co.olivepay.funding.global.handler.AppException;
import kr.co.olivepay.funding.global.properties.FundingProperties;
import kr.co.olivepay.funding.global.response.SuccessResponse;
import kr.co.olivepay.funding.global.enums.SuccessCode;
import kr.co.olivepay.funding.mapper.FundingMapper;
import kr.co.olivepay.funding.mapper.FundingUsageMapper;
import kr.co.olivepay.funding.openapi.service.FintechService;
import kr.co.olivepay.funding.repository.FundingRepository;
import kr.co.olivepay.funding.repository.FundingUsageRepository;
import kr.co.olivepay.funding.service.FundingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundingServiceImpl implements FundingService {

	private final FintechService fintechService;

	private final FundingRepository fundingRepository;
	private final FundingUsageRepository fundingUsageRepository;

	private final FundingMapper fundingMapper;
	private final FundingUsageMapper fundingUsageMapper;

	private final FundingProperties fundingProperties;

	private static final String DEPOSIT_TRANSACTION_SUMMARY = "쿠폰 잔액 입금";
	private static final String WITHDRAWAL_TRANSACTION_SUMMARY = "쿠폰 잔액 출금";

	/**
	 * 공용 기부금 총액 조회
	 * @return
	 */
	@Override
	public SuccessResponse<FundingAmountRes> getTotalFundingAmount() {
		Long amount = fundingRepository.sumTotalAmount();
		FundingAmountRes response = fundingMapper.toFundingAmountRes(amount != null ? amount : 0L);
		return new SuccessResponse<>(SuccessCode.TOTAL_FUNDING_AMOUNT_SUCCESS, response);
	}

	/**
	 * 공용 기부금 사용 내역 조회
	 * @return
	 */
	@Override
	public SuccessResponse<List<FundingUsageRes>> getDonationUsageHistory() {
		List<FundingUsage> usageList = fundingUsageRepository.findAll();
		List<FundingUsageRes> response = fundingUsageMapper.toFundingUsageResList(usageList);
		return new SuccessResponse<>(SuccessCode.FUNDING_USAGE_SEARCH_SUCCESS, response);
	}

	/**
	 * 공용 기부금으로 잔액 이체 (개발용)
	 * @param request
	 * @return
	 */
	@Override
	public SuccessResponse<NoneResponse> createFunding(FundingCreateReq request) {
		validateFunding(request.couponUserId());

		Funding funding = fundingMapper.toEntity(request);

		fintechService.transferAccount(request.userKey(), fundingProperties.getDepositAccountNo(), request.amount()
																										  .toString(),
			fundingProperties.getWithdrawalAccountNo(),
			DEPOSIT_TRANSACTION_SUMMARY, WITHDRAWAL_TRANSACTION_SUMMARY);
		fundingRepository.save(funding);
		return new SuccessResponse<>(SuccessCode.FUNDING_REGISTER_SUCCESS, NoneResponse.NONE);
	}

	private void validateFunding(Long couponMemberId) {
		if (fundingRepository.existsByCouponUserId(couponMemberId)) {
			throw new AppException(ErrorCode.DONATION_DUPLICATED);
		}
	}

	/**
	 * 기부 (개발용)
	 * @param request
	 * @return
	 */
	@Override
	@Transactional
	public SuccessResponse<NoneResponse> createFundingUsage(FundingUsageCreateReq request) {
		validateDonationAmount(request.amount());
		FundingUsage fundingUsage = fundingUsageMapper.toEntity(request);
		fundingUsageRepository.save(fundingUsage);
		return new SuccessResponse<>(SuccessCode.FUNDING_USAGE_REGISTER_SUCCESS, NoneResponse.NONE);
	}

	private void validateDonationAmount(Long donationAmount) {
		Long fundingTotalAmount = fundingRepository.sumTotalAmount();
		Long fundingUsageTotalAmount = fundingUsageRepository.sumTotalAmount();
		if (fundingTotalAmount == null) {
			throw new AppException(ErrorCode.DONATION_AMOUNT_NOT_AVAILABLE);
		}
		Long availableAmount =
			fundingTotalAmount - (fundingUsageTotalAmount != null ? fundingUsageTotalAmount : 0L);
		if (availableAmount < donationAmount) {
			throw new AppException(ErrorCode.DONATION_AMOUNT_NOT_AVAILABLE);
		}
	}
}
