package kr.co.olivepay.donation.service;

import kr.co.olivepay.core.donation.dto.req.CouponListReq;
import kr.co.olivepay.core.donation.dto.res.CouponRes;
import kr.co.olivepay.donation.dto.req.DonationMyReq;
import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.dto.res.CouponMyRes;
import kr.co.olivepay.donation.dto.res.DonationMyRes;
import kr.co.olivepay.donation.dto.res.DonationTotalRes;
import kr.co.olivepay.donation.global.enums.NoneResponse;
import kr.co.olivepay.donation.global.enums.SuccessCode;
import kr.co.olivepay.core.global.dto.res.PageResponse;
import kr.co.olivepay.donation.global.response.SuccessResponse;

import java.util.List;

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

    /**
     * 내 후원 내역 조회 메소드
     * @param request 후원자의 이메일과 전화번호 {@link DonationMyReq}
     * @param index 페이지 인덱스
     * @return 내 후원 내역 최신순 20개와 다음 페이지 번호
     */
    SuccessResponse<PageResponse<List<DonationMyRes>>> getMyDonation(DonationMyReq request, Long index);

    /**
     * 가맹점의 쿠폰 갯수를 쿠폰 단위별로 조회하는 메소드
     * @param franchiseId 조회하고자 하는 가맹점의 아이디
     * @return 가맹점 아이디, 2000/4000 쿠폰 갯수 {@link CouponRes}
     */
    SuccessResponse<CouponRes> getFranchiseCoupon(Long franchiseId);

    /**
     * 가맹점들의 쿠폰 갯수를 쿠폰 단위별로 조회하는 메소드
     * @param request 가맹점의 아이디 리스트
     * @return 가맹점 아이디, 2000/4000 쿠폰 갯수의 리스트 형태 {@link CouponRes}
     */
    SuccessResponse<List<CouponRes>> getFranchiseListCoupon(CouponListReq request);

    /**
     * 특정 유저가 가진 쿠폰 중 사용하지 않은 쿠폰을 조회하는 메소드
     * @param memberId 쿠폰 조회를 할 멤버의 아이디
     * @param franchiseId 쿠폰 조회를 하고 싶은 특정 가맹점의 아이디 (NULL 가능)
     * @return
     */
    SuccessResponse<List<CouponMyRes>> getMyCoupon(Long memberId, Long franchiseId);
}
