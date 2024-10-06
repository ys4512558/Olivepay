package kr.co.olivepay.franchise.service.impl;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.olivepay.core.donation.dto.req.CouponListReq;
import kr.co.olivepay.core.donation.dto.res.CouponRes;
import kr.co.olivepay.core.franchise.dto.res.FranchiseMyDonationRes;
import kr.co.olivepay.franchise.client.CouponServiceClient;
import kr.co.olivepay.franchise.global.enums.ErrorCode;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.handler.AppException;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.global.utils.GeoUtil;
import kr.co.olivepay.franchise.service.FranchiseService;
import kr.co.olivepay.franchise.service.LikeService;
import kr.co.olivepay.franchise.service.ReviewService;
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

	private static final Long RANGE_METERS = 500L;

	private final FranchiseRepository franchiseRepository;
	private final FranchiseMapper franchiseMapper;
	private final LikeService likeService;
	private final ReviewService reviewService;
	private final CouponServiceClient couponServiceClient;

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

	private void validateRegistrationNumber(String registrationNumber) {
		if (franchiseRepository.existsByRegistrationNumber(registrationNumber)) {
			throw new AppException(ErrorCode.FRANCHISE_REGISTRATION_NUMBER_DUPLICATED);
		}
	}

	/**
	 * 가맹점 검색
	 * @param latitude
	 * @param longitude
	 * @param category
	 * @return
	 */
	@Override
	public SuccessResponse<List<FranchiseBasicRes>> getFranchiseList(Double latitude, Double longitude,
		Category category) {
		Double latRange = GeoUtil.metersToLatitude(RANGE_METERS);
		Double lonRange = GeoUtil.metersToLongitude(RANGE_METERS, latitude);
		List<Franchise> franchiseList = franchiseRepository.findByLocationAndCategory(latitude, longitude, latRange, lonRange, category);
		List<CouponRes> couponResList = getCouponsForFranchises(franchiseList);
		List<FranchiseBasicRes> response = buildFranchiseBasicResList(franchiseList, couponResList);
		return new SuccessResponse<>(SuccessCode.FRANCHISE_SEARCH_SUCCESS, response);
	}

	private List<CouponRes> getCouponsForFranchises(List<Franchise> franchiseList) {
		List<Long> franchiseIdList = franchiseList.stream()
												  .map(Franchise::getId)
												  .toList();
		CouponListReq request = franchiseMapper.toCouponListReq(franchiseIdList);

		List<CouponRes> couponResList = null;
		try {
			couponResList = couponServiceClient.getFranchiseCouponList(request)
											   .data();
		} catch (Exception e) {
			throw new AppException(ErrorCode.COUPON_FEIGN_CLIENT_ERROR);
		}

		return couponResList;
	}

	private List<FranchiseBasicRes> buildFranchiseBasicResList(List<Franchise> franchiseList,
		List<CouponRes> couponResList) {
		Map<Long, Long> couponMap = createCouponMap(couponResList);
		return franchiseList.stream()
							.map(franchise -> buildFranchiseBasicRes(franchise, couponMap))
							.toList();
	}

	private Map<Long, Long> createCouponMap(List<CouponRes> couponResList) {
		return couponResList.stream()
							.collect(Collectors.toMap(
								CouponRes::franchiseId,
								coupon -> coupon.coupon2() + coupon.coupon4()
							));
	}

	private FranchiseBasicRes buildFranchiseBasicRes(Franchise franchise, Map<Long, Long> couponMap) {
		Long coupons = couponMap.getOrDefault(franchise.getId(), 0L);
		Integer likes = likeService.getLikesCount(franchise.getId());
		Float avgStars = reviewService.getAvgStars(franchise.getId());
		return franchiseMapper.toFranchiseBasicRes(franchise, likes, coupons, avgStars);
	}

	/**
	 * 가맹점 상세 정보 조회
	 * @param franchiseId
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public SuccessResponse<FranchiseDetailRes> getFranchiseDetail(Long memberId, String role, Long franchiseId) {
		Franchise franchise = franchiseRepository.getById(franchiseId);

		CouponRes couponRes = null;
		try {
			couponRes = couponServiceClient.getFranchiseCoupon(franchiseId)
										   .data();
		} catch (Exception e) {
			throw new AppException(ErrorCode.COUPON_FEIGN_CLIENT_ERROR);
		}

		Long coupon2 = couponRes.coupon2();
		Long coupon4 = couponRes.coupon4();
		Integer likes = likeService.getLikesCount(franchiseId);
		Boolean isLiked = null;
		if (role.equals("USER"))
			isLiked = likeService.getLiked(memberId, franchiseId);

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
												 .orElseThrow(
													 () -> new AppException(ErrorCode.FRANCHISE_NOT_FOUND_BY_ID));
		FranchiseMinimalRes response = franchiseMapper.toFranchiseMinimalRes(franchise);
		return new SuccessResponse<>(SuccessCode.FRANCHISE_SEARCH_SUCCESS, response);
	}

	/**
	 * 사용자 id > 가맹점 id
	 * @param memberId
	 * @return
	 */
	@Override
	public SuccessResponse<FranchiseMinimalRes> getFranchiseByMemberId(Long memberId) {
		Franchise franchise = franchiseRepository.findByMemberId(memberId)
												 .orElseThrow(
													 () -> new AppException(ErrorCode.FRANCHISE_NOT_FOUND_BY_OWNER));
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

	/**
	 * 가맹점 id list > 가맹점 데이터(id, 상호명, 주소) list
	 * @param franchiseIdList
	 * @return
	 */
	@Override
	public SuccessResponse<List<FranchiseMyDonationRes>> getFranchiseListByFranchiseIdList(List<Long> franchiseIdList) {
		List<Franchise> franchiseList = franchiseRepository.findAllById(franchiseIdList);
		List<FranchiseMyDonationRes> response = franchiseMapper.toFranchiseMyDonationResList(franchiseList);
		return new SuccessResponse<>(SuccessCode.SUCCESS, response);
	}

}
