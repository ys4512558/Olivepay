package kr.co.olivepay.donation.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import kr.co.olivepay.core.donation.dto.req.CouponListReq;
import kr.co.olivepay.core.donation.dto.res.CouponRes;
import kr.co.olivepay.core.franchise.dto.req.FranchiseIdListReq;
import kr.co.olivepay.core.franchise.dto.res.FranchiseMyDonationRes;
import kr.co.olivepay.core.global.dto.res.PageResponse;
import kr.co.olivepay.donation.client.FranchiseServiceClient;
import kr.co.olivepay.donation.dto.req.CouponGetReq;
import kr.co.olivepay.donation.dto.req.DonationMyReq;
import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.dto.res.CouponDetailRes;
import kr.co.olivepay.donation.dto.res.CouponMyRes;
import kr.co.olivepay.donation.dto.res.DonationMyRes;
import kr.co.olivepay.donation.dto.res.DonationTotalRes;
import kr.co.olivepay.donation.entity.Coupon;
import kr.co.olivepay.donation.entity.CouponUser;
import kr.co.olivepay.donation.entity.Donation;
import kr.co.olivepay.donation.entity.Donor;
import kr.co.olivepay.donation.enums.CouponUnit;
import kr.co.olivepay.donation.global.enums.NoneResponse;
import kr.co.olivepay.donation.global.handler.AppException;
import kr.co.olivepay.donation.global.response.SuccessResponse;
import kr.co.olivepay.donation.mapper.CouponMapper;
import kr.co.olivepay.donation.mapper.CouponUserMapper;
import kr.co.olivepay.donation.mapper.DonationMapper;
import kr.co.olivepay.donation.mapper.DonorMapper;
import kr.co.olivepay.donation.repository.CouponRepository;
import kr.co.olivepay.donation.repository.CouponUserRepository;
import kr.co.olivepay.donation.repository.DonationRepository;
import kr.co.olivepay.donation.repository.DonorRepository;
import kr.co.olivepay.donation.service.DonationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.co.olivepay.donation.global.enums.ErrorCode.*;
import static kr.co.olivepay.donation.global.enums.NoneResponse.NONE;
import static kr.co.olivepay.donation.global.enums.SuccessCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {
    private final DonorRepository donorRepository;
    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final DonorMapper donorMapper;
    private final CouponMapper couponMapper;
    private final CouponUserMapper couponUserMapper;
    private final CouponRepository couponRepository;
    private final CouponUserRepository couponUserRepository;
    private final FranchiseServiceClient franchiseServiceClient;
    private final EntityManager entityManager;

    private final int COUPON_MAX = 2;
    private final int MAX_RETRY = 3;


    @Override
    @Transactional
    public SuccessResponse<NoneResponse> donate(DonationReq request) {
        // 전화번호로 식별 후 이메일 갱신 또는 삽입하여 donor 객체 생성
        Donor donor = updateOrCreateDonor(request);
        // 후원 객체 생성
        Donation donation = donationRepository.save(donationMapper.toEntity(donor, request));
        // 쿠폰 생성
        couponRepository.save(couponMapper.toEntity(donation, CouponUnit.TWO, request));
        couponRepository.save(couponMapper.toEntity(donation, CouponUnit.FOUR, request));

        // TODO : 기부금액을 핀테크 api를 사용하여 계좌이체 처리
        return new SuccessResponse<>(DONATION_SUCCESS, NONE);
    }

    @Override
    public SuccessResponse<DonationTotalRes> getDonationTotal() {
        Long total = donationRepository.sumMoney();
        Long mealCount = couponUserRepository.countByIsUsed(true);
        DonationTotalRes response = DonationTotalRes.builder()
                                                    .total(total)
                                                    .mealCount(mealCount)
                                                    .build();
        return new SuccessResponse<>(DONATION_TOTAL_SUCCESS, response);
    }

    @Override
    public SuccessResponse<PageResponse<List<DonationMyRes>>> getMyDonation(DonationMyReq request, Long index) {
        List<DonationMyRes> response = new ArrayList<>();
        Long nextIndex = index;
        Optional<Donor> donor = donorRepository.findByEmailAndPhoneNumber(request.email(), request.phoneNumber());
        // 후원자가 있는 경우에만 후원 내역 조회
        if (donor.isPresent()) {
            List<Donation> donations = donationRepository.getMyDonation(donor.get(), index);
            nextIndex = donations.isEmpty() ? nextIndex : donations.get(donations.size() - 1)
                                                                   .getId();
            List<Long> franchiseIds = extractFranchiseIdsByDonations(donations);
            List<FranchiseMyDonationRes> franchiseResponse = getFranchiseMyDonationRes(franchiseIds);
            // Donation과 franchiseResponse를 통해 DonationMyRes로 매핑
            response = donations.stream()
                                .map(donation -> mapToDonationMyRes(donation, franchiseResponse))
                                .toList();
        }
        return new SuccessResponse<>(DONATION_MY_SUCCESS, new PageResponse<>(nextIndex, response));
    }


    @Override
    public SuccessResponse<CouponDetailRes> getFranchiseCoupon(Long franchiseId) {
        List<CouponRes> couponRes = couponRepository.getCouponCountsByFranchiseId(List.of(franchiseId));
        String franchiseName = getFranchiseMyDonationRes(List.of(franchiseId)).get(0)
                                                                              .name();
        CouponDetailRes response = couponMapper.toCouponDetailRes(franchiseName, couponRes.get(0));
        return new SuccessResponse<>(COUPON_GET_SUCCESS, response);
    }

    @Override
    public SuccessResponse<List<CouponRes>> getFranchiseListCoupon(CouponListReq request) {
        List<CouponRes> couponRes = couponRepository.getCouponCountsByFranchiseId(request.franchiseIdList());
        return new SuccessResponse<>(COUPON_LIST_GET_SUCCESS, couponRes);
    }

    @Override
    public SuccessResponse<List<CouponMyRes>> getMyCoupon(Long memberId, Long franchiseId) {
        List<CouponUser> coupons = couponUserRepository.findCouponsByMemberIdAndUnused(memberId, franchiseId);
        List<Long> franchiseIds = extractFranchiseIdsByCouponUsers(coupons);
        List<FranchiseMyDonationRes> franchiseResponse = getFranchiseMyDonationRes(franchiseIds);
        List<CouponMyRes> response = mapToCouponMyResList(coupons, franchiseResponse);
        return new SuccessResponse<>(COUPON_MY_LIST_GET_SUCCESS, response);
    }

    @Override
    @Transactional
    public SuccessResponse<NoneResponse> getCoupon(Long memberId, CouponGetReq request) {
        log.info("쿠폰 획득 로직 시작");
        log.info("쿠폰 획득 로직 시작 : [{}]", memberId);
        log.info("쿠폰 획득 로직 시작 : [{}]", request);
        if (couponUserRepository.countByMemberIdAndIsUsed(memberId, false) >= COUPON_MAX)
            throw new AppException(COUPON_MAX_EXCEED);

        log.info("coupons 획득");
        List<Coupon> coupons = couponRepository.findAllByCouponUnitAndFranchiseId(
                CouponUnit.findByValue(request.couponUnit()), request.franchiseId());
        log.info("coupons 획득 : [{}]", coupons);
        if (coupons.isEmpty()) throw new AppException(COUPON_IS_NOT_EXIST);
        log.info("coupons 개수 : [{}] ", coupons.size());
        Coupon targetCoupon = coupons.get(0);
        log.info("coupons 개수 : [{}] ", coupons.size());
        if (!getCouponTry(targetCoupon)) throw new AppException(COUPON_GET_FAIL);
        log.info("CouponUser 저장 시도");
        couponUserRepository.save(couponUserMapper.toEntity(targetCoupon, memberId));
        log.info("CouponUser 저장");
        return new SuccessResponse<>(COUPON_OBTAIN_SUCCESS, NONE);
    }

    /**
     * 쿠폰 획득 시도 메소드
     * @param coupon 획득을 시도할 쿠폰
     * @return 획득 성공 여부 반환
     */
    @Transactional
    public boolean getCouponTry(Coupon coupon) {
        log.info("getCouponTry 시작");
        int attempt = 0;
        while (attempt < MAX_RETRY) {
            log.info("getCouponTry attempt : [{}]", attempt);
            try {
                log.info("coupon.getCount(): [{}]", coupon.getCount());
                if (coupon.getCount() == 0) return false;
                log.info("coupon.getCount(): [{}]", coupon.getCount());
                log.info("couponRepository.decreaseCouponCount(): [{}]", coupon);
                int updatedRows = couponRepository.decreaseCouponCount(coupon.getId(), coupon.getVersion());
                log.info("updateRows : [{}]", updatedRows);
                if (updatedRows == 1) {
                    log.info("updateRows : [{}]", updatedRows);
//                    entityManager.refresh(coupon);
                    log.info("entityManager.refresh(coupon)");
                    return true;
                }
            } catch (OptimisticLockException e) {
                attempt++;
                log.error("Coupon OptimisticLockException :  쿠폰 획득 실패 재시도중 : {}회", attempt);
            }
        }
        return false;
    }

    /**
     * feignClient 예외 처리를 고려한 응답 객체를 반환하는 메소드
     *
     * @param franchiseIds 가맹점 정보 리스트를 조회하고싶은 가맹점 id 리스트
     * @return 가맹점 정보 리스트 {@link FranchiseMyDonationRes}
     */
    private List<FranchiseMyDonationRes> getFranchiseMyDonationRes(List<Long> franchiseIds) {
        try {
            return franchiseServiceClient.getFranchiseInfos(new FranchiseIdListReq(franchiseIds))
                                         .data();
        } catch (Exception e) {
            throw new AppException(FRANCHISE_FEIGN_CLIENT_ERROR);
        }
    }


    /**
     * 전화번호 존재여부에 따라 이메일을 새로 추가 또는 갱신하여 후원자 정보를  메소드
     *
     * @param request 후원자 정보가 담긴 요청 객체
     * @return 새로 추가 또는 갱신 된 후원자 객체 {@link Donor}
     */
    private Donor updateOrCreateDonor(DonationReq request) {
        Donor donor = donorRepository.findByPhoneNumber(request.phoneNumber())
                                     // 존재하는 경우 이메일 업데이트
                                     .map(existingDonor -> {
                                         existingDonor.updateEmail(request.email());
                                         return existingDonor;
                                     })
                                     // 존재하지 않는 경우 새로운 Donor 생성
                                     .orElseGet(() -> donorMapper.toEntity(request));
        return donorRepository.save(donor);
    }

    /**
     * 후원 정보와 해당 가맹점 리스트를 통해 내 후원 내역 정보를 매핑하는 메소드
     *
     * @param donation          후원 객체
     * @param franchiseResponse 후원의 franchiseId를 가지고 받아낸 가맹점 정보 리스트
     * @return 내 후원 내역 리스트 {@link DonationMyRes}
     */
    private DonationMyRes mapToDonationMyRes(Donation donation, List<FranchiseMyDonationRes> franchiseResponse) {
        Optional<FranchiseMyDonationRes> franchiseOpt =
                franchiseResponse.stream()
                                 .filter(fr -> fr.franchiseId()
                                                 .equals(donation.getFranchiseId()))
                                 .findFirst();
        return franchiseOpt.map(franchiseRes -> donationMapper.toDonationMyRes(donation, franchiseRes))
                           .get();
    }

    /**
     * 쿠폰 유저 객체 리스트와 가맹점 정보 객체 리스트를 통해 사용자가 보유한 쿠폰 리스트 객체로 변환하는 메소드
     *
     * @param couponUsers 매핑할 쿠폰 유저 리스트
     * @param franchises  매핑할 가맹점 정보 리스트
     * @return {@link CouponMyRes}
     */
    private List<CouponMyRes> mapToCouponMyResList(
            List<CouponUser> couponUsers,
            List<FranchiseMyDonationRes> franchises
    ) {
        // 받아온 franchise 정보를 <id, franchiseName> 으로 매핑
        Map<Long, String> franchiseIdToNameMap = franchises.stream()
                                                           .collect(Collectors.toMap(
                                                                   FranchiseMyDonationRes::franchiseId,
                                                                   FranchiseMyDonationRes::name,
                                                                   (existing, replacement) -> existing
                                                           ));

        // CouponUser 리스트를 CouponMyRes로 변환
        return couponUsers.stream()
                          .map(couponUser -> {
                              Long franchiseId = couponUser.getCoupon()
                                                           .getFranchiseId();
                              // franchiseId를 통해 해당하는 franchiseName 찾기 (없으면 null)
                              String franchiseName = franchiseIdToNameMap.getOrDefault(franchiseId, "");
                              return couponUserMapper.toCouponMyRes(couponUser, franchiseId, franchiseName);
                          })
                          .collect(Collectors.toList());
    }

    /**
     * franchise Id 리스트를 통해 franchise의 정보를 요청하기 위해 franchise 아이디를 추출하는 메소드
     *
     * @param coupons franchise Id 리스트를 통해 franchise의 정보를 요청하기 위해 franchise 아이디를 추출할 couponUser 리스트
     * @return franchiseId 리스트
     */
    private List<Long> extractFranchiseIdsByCouponUsers(List<CouponUser> coupons) {
        return coupons.stream()
                      .map(couponUser -> couponUser.getCoupon()
                                                   .getFranchiseId())
                      .collect(Collectors.toList());
    }

    /**
     * franchise Id 리스트를 통해 franchise의 정보를 요청하기 위해 franchise 아이디를 추출하는 메소드
     *
     * @param donations franchise Id 리스트를 통해 franchise의 정보를 요청하기 위해 franchise 아이디를 추출할 Donation 리스트
     * @return franchiseId 리스트
     */
    private List<Long> extractFranchiseIdsByDonations(List<Donation> donations) {
        return donations.stream()
                        .map(Donation::getFranchiseId)
                        .collect(Collectors.toList());
    }
}
