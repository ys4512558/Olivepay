package kr.co.olivepay.franchise.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.olivepay.franchise.global.enums.ErrorCode;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.handler.AppException;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.service.FranchiseService;
import kr.co.olivepay.franchise.service.LikeService;
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
public class FranchiseServiceImpl implements FranchiseService {

	private final FranchiseRepository franchiseRepository;
	private final FranchiseMapper franchiseMapper;
	private final LikeService likeService;

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

	/**
	 * 가맹점 상세 정보 조회
	 * @param franchiseId
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public SuccessResponse<FranchiseDetailRes> getFranchiseDetail(Long franchiseId) {
		Long memberId = 1L;//TODO: memberId, Role 필요

		Franchise franchise = franchiseRepository.getById(franchiseId);

		//TODO: Coupon 서비스와 연결
		Integer coupon2 = 1;
		Integer coupon4 = 2;

		Integer likes = likeService.getLikesCount(franchiseId);
		Boolean isLiked = likeService.getLiked(memberId, franchiseId);

		FranchiseDetailRes response = franchiseMapper.toFranchiseDetailRes(franchise, coupon2, coupon4, likes, isLiked);
		return new SuccessResponse<>(SuccessCode.FRANCHISE_DETAIL_SUCCESS, response);
	}

	/**
	 * 가맹점 id > 가맹점 상호명
	 * @param franchiseId
	 * @return
	 */
	@Override
	public SuccessResponse<FranchiseMinimalRes> getFranchiseByFranchiseId(Long franchiseId) {
		Franchise franchise = franchiseRepository.findById(franchiseId)
												 .orElseThrow(() -> new AppException(ErrorCode.FRANCHISE_NOT_FOUND_BY_ID));
		FranchiseMinimalRes response = franchiseMapper.toFranchiseMinimalRes(franchise);
		return new SuccessResponse<>(SuccessCode.FRANCHISE_SEARCH_SUCCESS, response);
	}

	/**
	 * 사용자 id > 가맹점 id
	 * @param memberId
	 * @return
	 */
	//TODO: 토큰이 있어야 테스트 가능
	@Override
	public SuccessResponse<FranchiseMinimalRes> getFranchiseByMemberId(Long memberId) {
		Franchise franchise = franchiseRepository.findByMemberId(memberId)
												 .orElseThrow(() -> new AppException(ErrorCode.FRANCHISE_NOT_FOUND_BY_OWNER));
		FranchiseMinimalRes response = franchiseMapper.toFranchiseMinimalRes(franchise);
		return new SuccessResponse<>(SuccessCode.FRANCHISE_SEARCH_SUCCESS, response);
	}

	/**
	 * 사업자등록번호 중복 체크
	 * @param registrationNumber
	 * @return
	 */
	@Override
	public SuccessResponse<ExistenceRes> checkRegistrationNumberDuplication(String registrationNumber) {
		boolean isExist = franchiseRepository.existsByRegistrationNumber(registrationNumber);
		ExistenceRes response = franchiseMapper.toExistenceRes(isExist);
		return new SuccessResponse<>(SuccessCode.REGISTRATION_NUMBER_CHECK_SUCCESS, response);
	}


}
