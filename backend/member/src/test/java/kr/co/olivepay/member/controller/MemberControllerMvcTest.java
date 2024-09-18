package kr.co.olivepay.member.controller;

import kr.co.olivepay.member.dto.res.DuplicateRes;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static kr.co.olivepay.member.global.enums.SuccessCode.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@TestPropertySource(properties = {"server.port=8080"})
@AutoConfigureMockMvc(addFilters = false) // Security Filter 비활성화
public class MemberControllerMvcTest {

    @MockBean
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;

    private final String SUCCESS = "SUCCESS", ERROR = "ERROR";

    @Test
    @DisplayName("전화번호 중복 확인 성공 - 중복됨")
    public void 전화번호_중복_확인_성공_중복됨() throws Exception {
        // given
        String phone = "01011112222";
        SuccessResponse<DuplicateRes> success =
                new SuccessResponse<>(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS,
                        new DuplicateRes(true));

        // when
        when(memberService.checkPhoneNumberDuplicate(phone)).thenReturn(success);

        // then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/members/duplicates/phone")
                                      .param("phoneNumber", phone)
        );

        actions.andExpect(status().is(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS.getHttpStatus().value()))
               .andExpect(jsonPath("$.resultCode").value(SUCCESS))
               .andExpect(jsonPath("$.code").value(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS.name()))
               .andExpect(jsonPath("$.message").value(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS.getMessage()))
               .andExpect(jsonPath("$.data.isDuplicate").value(true))
               .andDo(print());
    }

    @Test
    @DisplayName("전화번호 중복 확인 성공 - 중복 아님")
    public void 전화번호_중복_확인_성공_중복_아님() throws Exception {
        // given
        String phone = "01011112222";
        SuccessResponse<DuplicateRes> success =
                new SuccessResponse<>(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS,
                        new DuplicateRes(false));

        // when
        when(memberService.checkPhoneNumberDuplicate(phone)).thenReturn(success);

        // then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/members/duplicates/phone")
                                      .param("phoneNumber", phone)
        );

        actions.andExpect(status().is(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS.getHttpStatus().value()))
               .andExpect(jsonPath("$.resultCode").value(SUCCESS))
               .andExpect(jsonPath("$.code").value(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS.name()))
               .andExpect(jsonPath("$.message").value(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS.getMessage()))
               .andExpect(jsonPath("$.data.isDuplicate").value(false))
               .andDo(print());
    }

    @Test
    @DisplayName("전화번호 중복 확인 실패 - 전화번호가 11자리 미만")
    void 전화번호_중복_확인_실패_전화번호가_11자리_미만() throws Exception {
        // given
        String phone = "0123456789";
        SuccessResponse<DuplicateRes> success =
                new SuccessResponse<>(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS,
                        new DuplicateRes(true));

        // when
        when(memberService.checkPhoneNumberDuplicate(phone)).thenReturn(success);

        // then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/members/duplicates/phone")
                                      .param("phoneNumber", phone)
        );

        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("유효하지 않은 요청입니다."));
    }

    @Test
    @DisplayName("전화번호 중복 확인 실패 - 전화번호가 11자리 초과")
    void 전화번호_중복_확인_실패_전화번호가_11자리_초과() throws Exception {
        // given
        String phone = "000123456789";
        SuccessResponse<DuplicateRes> success =
                new SuccessResponse<>(CHECK_PHONE_NUMBER_DUPLICATE_SUCCESS,
                        new DuplicateRes(true));

        // when
        when(memberService.checkPhoneNumberDuplicate(phone)).thenReturn(success);

        // then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.get("/members/duplicates/phone")
                                      .param("phoneNumber", phone)
        );

        actions.andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.message").value("유효하지 않은 요청입니다."));
    }
}
