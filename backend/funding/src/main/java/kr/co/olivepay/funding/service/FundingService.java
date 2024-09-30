package kr.co.olivepay.funding.service;

import java.util.List;

import kr.co.olivepay.funding.dto.req.FundingCreateReq;
import kr.co.olivepay.funding.dto.req.FundingUsageCreateReq;
import kr.co.olivepay.funding.dto.res.FundingAmountRes;
import kr.co.olivepay.funding.dto.res.FundingUsageRes;
import kr.co.olivepay.funding.global.enums.NoneResponse;
import kr.co.olivepay.funding.global.response.Response;
import kr.co.olivepay.funding.global.response.SuccessResponse;

public interface FundingService {
	/**
	 * 공용 기부금 총액 조회
	 * @return
	 */
	SuccessResponse<FundingAmountRes> getTotalFundingAmount();

	/**
	 * 공용 기부금 사용 내역 조회
	 * @return
	 */
	SuccessResponse<List<FundingUsageRes>> getDonationUsageHistory();

	/**
	 * 공용 기부금으로 잔액 이동 (개발용)
	 * @param request
	 * @return
	 */
	SuccessResponse<NoneResponse> createFunding(FundingCreateReq request);

	/**
	 * 기부 (개발용)
	 * @param request
	 * @return
	 */
	SuccessResponse<NoneResponse> createFundingUsage(FundingUsageCreateReq request);
}
