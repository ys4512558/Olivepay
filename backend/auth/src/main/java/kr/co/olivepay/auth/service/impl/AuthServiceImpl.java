package kr.co.olivepay.auth.service.impl;

import feign.FeignException;
import kr.co.olivepay.auth.client.FranchiseClient;
import kr.co.olivepay.auth.dto.req.LoginReq;
import kr.co.olivepay.auth.dto.req.RefreshReq;
import kr.co.olivepay.auth.dto.res.OwnerLoginRes;
import kr.co.olivepay.auth.dto.res.RefreshRes;
import kr.co.olivepay.auth.dto.res.UserLoginRes;
import kr.co.olivepay.auth.entity.Member;
import kr.co.olivepay.auth.entity.Tokens;
import kr.co.olivepay.auth.enums.Role;
import kr.co.olivepay.auth.global.handler.AppException;
import kr.co.olivepay.auth.global.response.SuccessResponse;
import kr.co.olivepay.auth.repository.MemberRepository;
import kr.co.olivepay.auth.service.AuthService;
import kr.co.olivepay.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kr.co.olivepay.auth.global.enums.ErrorCode.*;
import static kr.co.olivepay.auth.global.enums.SuccessCode.AUTH_TOKEN_CHANGE_SUCCESS;
import static kr.co.olivepay.auth.global.enums.SuccessCode.LOGIN_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final FranchiseClient franchiseClient;

    /**
     * 유저 로그인 메소드
     * @param loginReq
     * @return
     */
    @Override
    public SuccessResponse<UserLoginRes> userLogin(LoginReq loginReq) {
        Member member = validatePhoneNumber(loginReq.phoneNumber());
        if(member.getRole() == Role.OWNER){
            throw new AppException(ACCESS_DENIED);
        }

        //matches(평문, 암호문)으로 확인
        boolean matches = passwordEncoder.matches(loginReq.password(), member.getPassword());
        if(!matches) throw new AppException(INVALID_LOGIN_REQUEST);

        Tokens tokens = tokenService.createTokens(member.getId(), member.getRole());

        UserLoginRes response = UserLoginRes.builder()
                                .accessToken(tokens.getAccessToken())
                                .refreshToken(tokens.getRefreshToken())
                                .role(member.getRole().name())
                                .build();

        return new SuccessResponse<>(LOGIN_SUCCESS, response);
    }

    /**
     * 가맹점주 로그인 메소드
     * @param loginReq
     * @return
     */
    @Override
    public SuccessResponse<OwnerLoginRes> ownerLogin(LoginReq loginReq) {
        Member member = validatePhoneNumber(loginReq.phoneNumber());
        if(member.getRole() != Role.OWNER){
            throw new AppException(ACCESS_DENIED);
        }

        //matches(평문, 암호문)으로 확인
        boolean matches = passwordEncoder.matches(loginReq.password(), member.getPassword());
        if(!matches) throw new AppException(INVALID_LOGIN_REQUEST);

        // Franchise Service 에서 franchiseID 받아오기
        Long franchiseId = getFranchiseId(member.getId());

        Tokens tokens = tokenService.createTokens(member.getId(), member.getRole());

        OwnerLoginRes response = OwnerLoginRes.builder()
                                            .accessToken(tokens.getAccessToken())
                                            .refreshToken(tokens.getRefreshToken())
                                            .franchiseId(franchiseId)
                                            .role(member.getRole().name())
                                            .build();

        return new SuccessResponse<>(LOGIN_SUCCESS, response);
    }

    /**
     * 리프레시 토큰을 통해 토큰(access, refresh) 재발급 <br>
     * RTR 방식을 위해 리프레시 토큰으로 액세스 토큰 발급 시 리프레시 토큰 갱신하여 저장 및 다시 반환
     * @param refreshReq
     * @return
     */
    @Override
    public SuccessResponse<RefreshRes> updateToken(RefreshReq refreshReq) {
        // 리프레시 토큰 유효성 검증
        Long memberId = tokenService.validateRefreshToken(refreshReq);

        // memberId 유효성 검증
        Member member = validateMemberId(memberId);

        // 토큰 재발급(access, refresh)
        Tokens tokens = tokenService.createTokens(memberId, member.getRole());

        RefreshRes response = RefreshRes.builder()
                                        .accessToken(tokens.getAccessToken())
                                        .refreshToken(tokens.getRefreshToken())
                                        .build();

        return new SuccessResponse<>(AUTH_TOKEN_CHANGE_SUCCESS, response);
    }


    /**
     * 전화번호 중복 검증 메소드
     * @param phoneNumber
     * @return
     */
    private Member validatePhoneNumber(String phoneNumber){
        return memberRepository.findByPhoneNumber(phoneNumber)
                               .orElseThrow(() -> new AppException(INVALID_LOGIN_REQUEST));
    }

    /**
     * memberId 유효성 검증 및 member 반환 메소드
     * @param memberId
     * @return
     */
    private Member validateMemberId(Long memberId){
        return memberRepository.findById(memberId)
                               .orElseThrow(() -> new AppException(MEMBER_NOT_FOUND));
    }

    /**
     * 가맹점 ID 조회 메소드, FeignClient 사용
     * @param memberId
     * @return
     */
    private Long getFranchiseId(Long memberId){
        try {
            return franchiseClient.getFranchiseByMemberId(memberId).data().id();
        } catch (FeignException e){
            log.error("FranchiseClient /api/franchises/id/{memberId} 접근 중 오류발생: {}",  e.getMessage());
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }
}
