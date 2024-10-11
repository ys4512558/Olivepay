package kr.co.olivepay.member.controller;

import kr.co.olivepay.core.member.dto.res.DuplicateRes;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static kr.co.olivepay.member.global.enums.SuccessCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;
    
    
    @Test
    @DisplayName("전화번호 중복 확인 성공 - 중복됨")
    void 전화번호_중복_확인_성공_중복됨() throws Exception {
        // given
        String phoneNumber = "isDuplicated";

        DuplicateRes duplicateRes = new DuplicateRes(true);
        SuccessResponse<DuplicateRes> success =
                new SuccessResponse<>(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS, duplicateRes);

        // when
        when(memberService.checkPhoneNumberDuplicate(phoneNumber)).thenReturn(success);

        // then
        ResponseEntity<Response<DuplicateRes>> responseEntity =
                memberController.checkPhoneNumberDuplicate(phoneNumber);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(responseEntity.getBody().code())
                .isEqualTo(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS.name());
        assertThat(responseEntity.getBody().data().isDuplicate()).isTrue();

        // verify
        verify(memberService, times(1)).checkPhoneNumberDuplicate(phoneNumber);
    }

    @Test
    @DisplayName("전화번호 중복 확인 성공 - 중복 아님")
    void 전화번호_중복_확인_성공_중복_아님() throws Exception {
        // given
        String phoneNumber = "isNotDuplicated";

        DuplicateRes duplicateRes = new DuplicateRes(false);
        SuccessResponse<DuplicateRes> success =
                new SuccessResponse<>(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS, duplicateRes);

        // when
        when(memberService.checkPhoneNumberDuplicate(phoneNumber)).thenReturn(success);

        // then
        ResponseEntity<Response<DuplicateRes>> responseEntity =
                memberController.checkPhoneNumberDuplicate(phoneNumber);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(responseEntity.getBody().code())
                .isEqualTo(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS.name());
        assertThat(responseEntity.getBody().data().isDuplicate()).isFalse();

        // verify
        verify(memberService, times(1)).checkPhoneNumberDuplicate(phoneNumber);
    }
}
