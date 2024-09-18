package kr.co.olivepay.member.service;

import kr.co.olivepay.member.dto.res.DuplicateRes;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.repository.MemberRepository;
import kr.co.olivepay.member.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static kr.co.olivepay.member.global.enums.SuccessCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

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
}
