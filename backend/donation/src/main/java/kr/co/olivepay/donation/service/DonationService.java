package kr.co.olivepay.donation.service;

import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.global.enums.NoneResponse;
import kr.co.olivepay.donation.global.response.SuccessResponse;

public interface DonationService {
    SuccessResponse<NoneResponse> donate(DonationReq request);
}
