package kr.co.olivepay.franchise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.co.olivepay.franchise.global.enums.ErrorCode;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.handler.AppException;
import kr.co.olivepay.franchise.global.response.Response;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;

import kr.co.olivepay.franchise.dto.req.FranchiseCreateReq;
import kr.co.olivepay.franchise.dto.res.*;
import kr.co.olivepay.franchise.entity.*;
import kr.co.olivepay.franchise.repository.*;
import kr.co.olivepay.franchise.mapper.FranchiseMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService{

	private final FranchiseRepository franchiseRepository;
	private final FranchiseMapper franchiseMapper;

	/**
	 * 가맹점 등록
	 * @param memberId
	 * @param request
	 * @return
	 */
	@Override
	@Transactional
	public SuccessResponse<NoneResponse> registerFranchise(Long memberId, FranchiseCreateReq request) {
		//사업자등록번호가 이미 존재하는지 확인한다.
		validateRegistrationNumber(request.registrationNumber());

		Franchise franchise = franchiseMapper.toEntity(memberId, request);
		franchiseRepository.save(franchise);

		return new SuccessResponse<>(SuccessCode.FRANCHISE_REGISTER_SUCCESS, NoneResponse.NONE);
	}

	private void validateRegistrationNumber(String registrationNumber){
		if (franchiseRepository.existsByRegistrationNumber(registrationNumber)) {
			throw new AppException(ErrorCode.FRANCHISE_REGISTRATION_NUMBER_DUPLICATED);
		}
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
