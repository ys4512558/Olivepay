package kr.co.olivepay.donation.service;

import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.dto.res.DonationTotalRes;
import kr.co.olivepay.donation.global.enums.NoneResponse;
import kr.co.olivepay.donation.global.enums.SuccessCode;
import kr.co.olivepay.donation.global.response.SuccessResponse;

public interface DonationService {
    /**
     *
     * 후원자의 정보를 받아 후원처리를 하는 메소드
     * @param request 후원자 정보를 받는 DTO {@link DonationReq}
     * @return 후원 성공 메시지 {@link SuccessCode}
     */
    SuccessResponse<NoneResponse> donate(DonationReq request);

    /**
     * 후원 통합 현황 조회 메소드
     * @return 후원 총액과 제공한 총 끼니수를 반환한다. {@link DonationTotalRes}
     */
    SuccessResponse<DonationTotalRes> getDonationTotal();
}
