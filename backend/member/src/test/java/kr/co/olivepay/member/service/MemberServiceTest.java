package kr.co.olivepay.member.service;

import kr.co.olivepay.member.dto.req.MemberRegisterReq;
import kr.co.olivepay.member.dto.res.DuplicateRes;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.enums.Role;
import kr.co.olivepay.member.global.handler.AppException;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.mapper.MemberMapper;
import kr.co.olivepay.member.repository.MemberRepository;
import kr.co.olivepay.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static kr.co.olivepay.member.global.enums.ErrorCode.PHONE_NUMBER_DUPLICATED;
import static kr.co.olivepay.member.global.enums.SuccessCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberMapper memberMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    @DisplayName("전화번호 중복 확인 성공 - 중복됨")
    void 전화번호_중복_확인_성공_중복됨() throws Exception {
        // given
        String phoneNumber = "isDuplicated";

        // when
        when(memberRepository.existsByPhoneNumber(any())).thenReturn(true);

        // then
        SuccessResponse<DuplicateRes> response = memberService.checkPhoneNumberDuplicate(phoneNumber);

        assertThat(response.successCode()).isEqualTo(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS);
        assertThat(response.data()).isNotNull();
        assertThat(response.data().isDuplicate()).isTrue();


        // verify
        verify(memberRepository, times(1)).existsByPhoneNumber(any());
    }

    @Test
    @DisplayName("전화번호 중복 확인 성공 - 중복 아님")
    void 전화번호_중복_확인_성공_중복_아님() throws Exception {
        // given
        String phoneNumber = "isNotDuplicated";

        // when
        when(memberRepository.existsByPhoneNumber(any())).thenReturn(false);

        // then
        SuccessResponse<DuplicateRes> response = memberService.checkPhoneNumberDuplicate(phoneNumber);

        assertThat(response.successCode()).isEqualTo(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS);
        assertThat(response.data()).isNotNull();
        assertThat(response.data().isDuplicate()).isFalse();

        // verify
        verify(memberRepository, times(1)).existsByPhoneNumber(any());
    }

    @Test
    @DisplayName("회원가입 성공")
    void 회원가입_성공() throws Exception {
        // given
        String name = "testName";
        String password = "testPassword";
        String hashedPassword = "hashedPassword";
        String phoneNumber = "01011112222";
        Role role = Role.TEMP_USER;

        MemberRegisterReq request = MemberRegisterReq.builder()
                .name(name)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();

        Member expectedMember = Member.builder()
                              .name(name)
                              .password(hashedPassword)
                              .phoneNumber(phoneNumber)
                              .role(role)
                              .build();

        // when
        when(memberRepository.existsByPhoneNumber(phoneNumber)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(hashedPassword); //  비밀번호가 반환 되어야 함
        when(memberMapper.toMember(request, hashedPassword, role)).thenReturn(expectedMember);
        when(memberRepository.save(expectedMember)).thenReturn(expectedMember);

        // then
        Member member = memberService.registerMember(request, role);

        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo(expectedMember.getName());
        assertThat(member.getPhoneNumber()).isEqualTo(expectedMember.getPhoneNumber());
        assertThat(member.getRole()).isEqualTo(expectedMember.getRole());
        assertThat(member.getPassword()).isEqualTo(expectedMember.getPassword());

        // verify
        verify(memberRepository).save(expectedMember); // 저장 검증
        verify(passwordEncoder).encode(password); // 비밀번호 인코딩 검증
    }

    @Test
    @DisplayName("회원가입 실패 - 전화번호 중복")
    void 회원가입_실패_전화번호_중복() throws Exception {
        // given
        String phoneNumber = "duplicatedNumber";
        Role role = Role.TEMP_USER;

        MemberRegisterReq request = MemberRegisterReq.builder()
                                                     .phoneNumber(phoneNumber)
                                                     .build();

        // when
        when(memberRepository.existsByPhoneNumber(phoneNumber)).thenReturn(true);

        // then
        Throwable thrown = catchThrowable(() -> memberService.registerMember(request, role));

        assertThat(thrown)
                .isInstanceOf(AppException.class)
                .hasFieldOrPropertyWithValue("errorCode", PHONE_NUMBER_DUPLICATED);


        // verify
        verify(memberRepository, never()).save(any()); // 저장 이전 실패
        verify(passwordEncoder, never()).encode(any()); // 인코딩 이전 실패
    }
}
