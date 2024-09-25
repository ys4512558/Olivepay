package kr.co.olivepay.franchise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.olivepay.franchise.dto.req.FranchiseCreateReq;
import kr.co.olivepay.franchise.dto.res.ExistenceRes;
import kr.co.olivepay.franchise.dto.res.FranchiseBasicRes;
import kr.co.olivepay.franchise.dto.res.FranchiseDetailRes;
import kr.co.olivepay.franchise.dto.res.FranchiseMinimalRes;
import kr.co.olivepay.franchise.entity.Category;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.response.SuccessResponse;

@Service
public interface FranchiseService {

	/**
	 * 가맹점 등록
	 * @param memberId
	 * @param request
	 * @return
	 */
	SuccessResponse<NoneResponse> registerFranchise(Long memberId, FranchiseCreateReq request);

	/**
	 * 가맹점 검색
	 * @param latitude
	 * @param longitude
	 * @param category
	 * @return
	 */
	SuccessResponse<List<FranchiseBasicRes>> getFranchiseList(Double latitude, Double longitude, Category category);

	/**
	 * 가맹점 상세 정보 조회
	 * @param franchiseId
	 * @return
	 */
	SuccessResponse<FranchiseDetailRes> getFranchiseDetail(Long franchiseId);

	/**
	 * 가맹점 id > 가맹점 상호명
	 * @param franchiseId
	 * @return
	 */
	SuccessResponse<FranchiseMinimalRes> getFranchiseByFranchiseId(Long franchiseId);

	/**
	 * 사용자 id > 가맹점 id
	 * @param memberId
	 * @return
	 */
	SuccessResponse<FranchiseMinimalRes> getFranchiseByMemberId(Long memberId);

	/**
	 * 사업자등록번호 중복 체크
	 * @param registrationNumber
	 * @return
	 */
	SuccessResponse<ExistenceRes> checkRegistrationNumberDuplication(String registrationNumber);
}
