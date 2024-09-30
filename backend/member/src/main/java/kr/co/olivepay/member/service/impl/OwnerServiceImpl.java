package kr.co.olivepay.member.service.impl;

import feign.FeignException;
import kr.co.olivepay.core.franchise.dto.req.FranchiseCreateReq;
import kr.co.olivepay.member.client.FranchiseClient;
import kr.co.olivepay.member.dto.req.OwnerRegisterReq;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.entity.Owner;
import kr.co.olivepay.member.enums.Role;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.handler.AppException;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.mapper.FranchiseMapper;
import kr.co.olivepay.member.mapper.OwnerMapper;
import kr.co.olivepay.member.repository.OwnerRepository;
import kr.co.olivepay.member.service.MemberService;
import kr.co.olivepay.member.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.olivepay.member.global.enums.ErrorCode.FRANCHISE_REGISTRATION_NUMBER_DUPLICATED;
import static kr.co.olivepay.member.global.enums.ErrorCode.INTERNAL_SERVER_ERROR;
import static kr.co.olivepay.member.global.enums.SuccessCode.OWNER_CREATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final MemberService memberService;
    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    private final FranchiseMapper franchiseMapper;
    private final FranchiseClient franchiseClient;

    /**
     * 가맹점 및 가맹점주를 등록하는 메소드<br>
     * 1. 가맹점 등록번호롤 중복여부 판단<br>
     * 2. 가맹점주 등록<br>
     * 3. 가맹점 등록<br>
     * @param request
     * @return
     */
    @Override
    public SuccessResponse<NoneResponse> registerOwnerAndFranchise(OwnerRegisterReq request) {
        // franchise number 중복 확인
        FranchiseCreateReq franchiseCreateReq = request.franchiseCreateReq();
        String registrationNumber = franchiseMapper.toRegistrationNumber(franchiseCreateReq);
        validateRegistrationNumber(registrationNumber);

        // owner 등록
        Owner owner = registerOwner(request);

        // todo: franchise 등록
        Long memberId = owner.getMember().getId();
        registerFranchise(memberId, franchiseCreateReq);

        return new SuccessResponse<>(OWNER_CREATED, NoneResponse.NONE);
    }

    /**
     * 가맹점 등록번호 중복 여부 판단 메소드
     * @param registrationNumber
     */
    private void validateRegistrationNumber(String registrationNumber){
        try {
            // 중복 여부 확인
            boolean isDuplicate = franchiseClient.checkRegistrationNumberDuplication(registrationNumber)
                                                 .data()
                                                 .isExist();

            // 중복된 경우 예외 발생
            if (isDuplicate) {
                throw new AppException(FRANCHISE_REGISTRATION_NUMBER_DUPLICATED);
            }
        } catch (FeignException e) {
            log.error("가맹점 등록 번호 중복 확인 중 오류 발생: {}", e.getMessage());
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 가맹점주 등록 메소드
     * @param request
     * @return
     */
    @Transactional
    public Owner registerOwner(OwnerRegisterReq request){
        // Member 생성
        Member savedMember = memberService.registerMember(request.memberRegisterReq(), Role.OWNER);

        // Owner 생성
        Owner owner = ownerMapper.toOwner(request, savedMember);

        // 저장
        return ownerRepository.save(owner);
    }

    /**
     * 가맹점 등록 메소드(FeignClient 호출)
     * @param memberId
     * @param request
     */
    private void registerFranchise(Long memberId, FranchiseCreateReq request){
        try {
            franchiseClient.registerFranchise(memberId, request);
        } catch (FeignException e){
            log.error("가맹점 등록 중 오류 발생: {}", e.getMessage());
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }
}
