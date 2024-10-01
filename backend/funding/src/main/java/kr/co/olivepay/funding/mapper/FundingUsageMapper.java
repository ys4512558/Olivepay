package kr.co.olivepay.funding.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import kr.co.olivepay.funding.dto.req.FundingUsageCreateReq;
import kr.co.olivepay.funding.dto.res.FundingUsageRes;
import kr.co.olivepay.funding.entity.FundingUsage;

@Mapper(componentModel = "spring")
public interface FundingUsageMapper {
	FundingUsage toEntity(FundingUsageCreateReq request);

	List<FundingUsageRes> toFundingUsageResList(List<FundingUsage> usageList);
}
