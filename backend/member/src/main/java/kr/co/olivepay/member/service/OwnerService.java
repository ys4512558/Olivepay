package kr.co.olivepay.member.service;

import kr.co.olivepay.member.dto.req.OwnerRegisterReq;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.SuccessResponse;

public interface OwnerService {

    SuccessResponse<NoneResponse> registerOwnerAndFranchise(OwnerRegisterReq ownerRegisterReq);
}
