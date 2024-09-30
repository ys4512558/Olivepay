package kr.co.olivepay.funding.mapper;

import org.mapstruct.Mapper;

import kr.co.olivepay.funding.dto.req.FundingCreateReq;
import kr.co.olivepay.funding.dto.res.FundingAmountRes;
import kr.co.olivepay.funding.entity.Funding;

@Mapper(componentModel = "spring")
public interface FundingMapper {
	Funding toEntity(FundingCreateReq request);

	FundingAmountRes toFundingAmountRes(Long amount);
}
