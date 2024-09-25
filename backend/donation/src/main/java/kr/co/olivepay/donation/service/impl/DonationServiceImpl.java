package kr.co.olivepay.donation.service.impl;

import jakarta.transaction.Transactional;
import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.entity.Donation;
import kr.co.olivepay.donation.entity.Donor;
import kr.co.olivepay.donation.enums.CouponUnit;
import kr.co.olivepay.donation.global.enums.NoneResponse;
import kr.co.olivepay.donation.global.enums.SuccessCode;
import kr.co.olivepay.donation.global.response.SuccessResponse;
import kr.co.olivepay.donation.mapper.CouponMapper;
import kr.co.olivepay.donation.mapper.DonationMapper;
import kr.co.olivepay.donation.mapper.DonorMapper;
import kr.co.olivepay.donation.repository.CouponRepository;
import kr.co.olivepay.donation.repository.DonationRepository;
import kr.co.olivepay.donation.repository.DonorRepository;
import kr.co.olivepay.donation.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {
    private final DonorRepository donorRepository;
    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final DonorMapper donorMapper;
    private final CouponMapper couponMapper;
    private final CouponRepository couponRepository;

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
        return new SuccessResponse<>(SuccessCode.DONATION_SUCCESS, NoneResponse.NONE);
    }

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
}
