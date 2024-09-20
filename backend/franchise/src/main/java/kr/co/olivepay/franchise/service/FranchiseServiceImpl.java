package kr.co.olivepay.franchise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;

import kr.co.olivepay.franchise.dto.req.FranchiseCreateReq;
import kr.co.olivepay.franchise.dto.res.*;
import kr.co.olivepay.franchise.entity.*;
import kr.co.olivepay.franchise.repository.*;
import kr.co.olivepay.franchise.mapper.FranchiseMapper;

@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService{

	private final FranchiseRepository franchiseRepository;
	private final FranchiseMapper franchiseMapper;

	@Override
	public SuccessResponse<NoneResponse> registerFranchise(Long memberId, FranchiseCreateReq request) {
		Franchise franchise = franchiseMapper.toEntity(memberId, request);
		franchiseRepository.save(franchise);
		return new SuccessResponse<>(SuccessCode.FRANCHISE_REGISTER_SUCCESS, NoneResponse.NONE);
	}

	@Override
	public SuccessResponse<List<FranchiseBasicRes>> getFranchiseList(Double latitude, Double longitude,
		Category category) {
		return null;
	}

	@Override
	public SuccessResponse<FranchiseDetailRes> getFranchiseDetail(Long franchiseId) {
		return null;
	}

	@Override
	public SuccessResponse<FranchiseMinimalRes> getFranchiseByFranchiseId(Long franchiseId) {
		return null;
	}

	@Override
	public SuccessResponse<FranchiseMinimalRes> getFranchiseByMemberId(Long memberId) {
		return null;
	}

}
