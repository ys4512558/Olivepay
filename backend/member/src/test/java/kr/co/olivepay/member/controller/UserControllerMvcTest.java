package kr.co.olivepay.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.member.dto.req.MemberRegisterReq;
import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.service.UserService;
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

import static kr.co.olivepay.member.global.enums.ErrorCode.BAD_REQUEST;
import static kr.co.olivepay.member.global.enums.SuccessCode.USER_CREATED;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@TestPropertySource(properties = {"server.port=8080"})
@AutoConfigureMockMvc(addFilters = false) // Security Filter 비활성화
public class UserControllerMvcTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String SUCCESS = "SUCCESS", ERROR = "ERROR";

    @Test
    @DisplayName("유저 회원가입 성공")
    void 유저_회원가입_성공() throws Exception {
        // given
        String name = "testName";
        String password = "TestPassword123@";
        String phoneNumber = "01011112222";
        String nickname = "testNickname";
        String pin = "000000";
        String birthdate = "19990303";

        MemberRegisterReq mRequest = MemberRegisterReq.builder()
                                                      .name(name)
                                                      .password(password)
                                                      .phoneNumber(phoneNumber)
                                                      .build();

        UserRegisterReq uRequest = UserRegisterReq.builder()
                                                  .pin(pin)
                                                  .birthdate(birthdate)
                                                  .nickname(nickname)
                                                  .memberRegisterReq(mRequest)
                                                  .build();

        SuccessResponse<NoneResponse> expectedResponse =
                new SuccessResponse<>(USER_CREATED, NoneResponse.NONE);

        // when
        when(userService.registerUser(uRequest)).thenReturn(expectedResponse);

        // then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/members/users/sign-up")
                                      .contentType("application/json")
                                      .content(objectMapper.writeValueAsString(uRequest))
        );

        actions.andExpect(status().isCreated())
               .andExpect(jsonPath("$.resultCode").value(SUCCESS))
               .andExpect(jsonPath("$.code").value(USER_CREATED.name()))
               .andExpect(jsonPath("$.message").value(USER_CREATED.getMessage()))
               .andDo(print());
    }

    @Test
    @DisplayName("유저 회원가입 실패 - 비밀번호 길이 16자리 초과")
    void 유저_회원가입_실패_비밀번호_길이() throws Exception {
        // given
        String name = "testName";
        String password = "invalidTestPasswordTooLong";
        String phoneNumber = "01011112222";
        String nickname = "testNickname";
        String pin = "000000";
        String birthdate = "19990303";

        MemberRegisterReq mRequest = MemberRegisterReq.builder()
                                                      .name(name)
                                                      .password(password)
                                                      .phoneNumber(phoneNumber)
                                                      .build();

        UserRegisterReq uRequest = UserRegisterReq.builder()
                                                  .pin(pin)
                                                  .birthdate(birthdate)
                                                  .nickname(nickname)
                                                  .memberRegisterReq(mRequest)
                                                  .build();

        // when & then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/members/users/sign-up")
                                      .contentType("application/json")
                                      .content(objectMapper.writeValueAsString(uRequest))
        );

        actions.andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.resultCode").value(ERROR))
               .andExpect(jsonPath("$.code").value(BAD_REQUEST.name()))
               .andExpect(jsonPath("$.message").value(BAD_REQUEST.getMessage()))
               .andExpect(jsonPath("$.data").value("비밀번호 형식이 유효하지 않습니다."))
               .andDo(print());
    }

    @Test
    @DisplayName("유저 회원가입 실패 - 전화번호 11자리 아님")
    void 유저_회원가입_실패_전화번호_길이() throws Exception {
        // given
        String name = "testName";
        String password = "TestPassword123@";
        String phoneNumber = "0101111";
        String nickname = "testNickname";
        String pin = "000000";
        String birthdate = "19990303";

        MemberRegisterReq mRequest = MemberRegisterReq.builder()
                                                      .name(name)
                                                      .password(password)
                                                      .phoneNumber(phoneNumber)
                                                      .build();

        UserRegisterReq uRequest = UserRegisterReq.builder()
                                                  .pin(pin)
                                                  .birthdate(birthdate)
                                                  .nickname(nickname)
                                                  .memberRegisterReq(mRequest)
                                                  .build();

        // when & then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/members/users/sign-up")
                                      .contentType("application/json")
                                      .content(objectMapper.writeValueAsString(uRequest))
        );

        actions.andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.resultCode").value(ERROR))
               .andExpect(jsonPath("$.code").value(BAD_REQUEST.name()))
               .andExpect(jsonPath("$.message").value(BAD_REQUEST.getMessage()))
               .andExpect(jsonPath("$.data").value("전화번호는 11자 입니다."))
               .andDo(print());
    }

    @Test
    @DisplayName("유저 회원가입 실패 - 이름 3자리 미만")
    void 유저_회원가입_실패_이름_길이() throws Exception {
        // given
        String name = "tt";
        String password = "TestPassword123@";
        String phoneNumber = "01011112222";
        String nickname = "testNickname";
        String pin = "000000";
        String birthdate = "19990303";

        MemberRegisterReq mRequest = MemberRegisterReq.builder()
                                                      .name(name)
                                                      .password(password)
                                                      .phoneNumber(phoneNumber)
                                                      .build();

        UserRegisterReq uRequest = UserRegisterReq.builder()
                                                  .pin(pin)
                                                  .birthdate(birthdate)
                                                  .nickname(nickname)
                                                  .memberRegisterReq(mRequest)
                                                  .build();

        // when & then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/members/users/sign-up")
                                      .contentType("application/json")
                                      .content(objectMapper.writeValueAsString(uRequest))
        );

        actions.andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.resultCode").value(ERROR))
               .andExpect(jsonPath("$.code").value(BAD_REQUEST.name()))
               .andExpect(jsonPath("$.message").value(BAD_REQUEST.getMessage()))
               .andExpect(jsonPath("$.data").value("이름은 최소 3자, 최대 30자 입니다."))
               .andDo(print());
    }

    @Test
    @DisplayName("유저 회원가입 실패 - 닉네임 3자리 미만")
    void 유저_회원가입_실패_닉네임_길이() throws Exception {
        // given
        String name = "testName";
        String password = "TestPassword123@";
        String phoneNumber = "01011112222";
        String nickname = "tt";
        String pin = "000000";
        String birthdate = "19990303";

        MemberRegisterReq mRequest = MemberRegisterReq.builder()
                                                      .name(name)
                                                      .password(password)
                                                      .phoneNumber(phoneNumber)
                                                      .build();

        UserRegisterReq uRequest = UserRegisterReq.builder()
                                                  .pin(pin)
                                                  .birthdate(birthdate)
                                                  .nickname(nickname)
                                                  .memberRegisterReq(mRequest)
                                                  .build();

        // when & then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/members/users/sign-up")
                                      .contentType("application/json")
                                      .content(objectMapper.writeValueAsString(uRequest))
        );

        actions.andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.resultCode").value(ERROR))
               .andExpect(jsonPath("$.code").value(BAD_REQUEST.name()))
               .andExpect(jsonPath("$.message").value(BAD_REQUEST.getMessage()))
               .andExpect(jsonPath("$.data").value("닉네임은 최소 3자, 최대 30자 입니다."))
               .andDo(print());
    }

    @Test
    @DisplayName("유저 회원가입 실패 - 핀번호 6자리 아님")
    void 유저_회원가입_실패_핀번호_길이() throws Exception {
        // given
        String name = "testName";
        String password = "TestPassword123@";
        String phoneNumber = "01011112222";
        String nickname = "testNickname";
        String pin = "00";
        String birthdate = "19990303";

        MemberRegisterReq mRequest = MemberRegisterReq.builder()
                                                      .name(name)
                                                      .password(password)
                                                      .phoneNumber(phoneNumber)
                                                      .build();

        UserRegisterReq uRequest = UserRegisterReq.builder()
                                                  .pin(pin)
                                                  .birthdate(birthdate)
                                                  .nickname(nickname)
                                                  .memberRegisterReq(mRequest)
                                                  .build();

        // when & then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/members/users/sign-up")
                                      .contentType("application/json")
                                      .content(objectMapper.writeValueAsString(uRequest))
        );

        actions.andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.resultCode").value(ERROR))
               .andExpect(jsonPath("$.code").value(BAD_REQUEST.name()))
               .andExpect(jsonPath("$.message").value(BAD_REQUEST.getMessage()))
               .andExpect(jsonPath("$.data").value("간편 결제 비밀번호는 6자 입니다."))
               .andDo(print());
    }

    @Test
    @DisplayName("유저 회원가입 실패 - 생년월일 8자리 아님")
    void 유저_회원가입_실패_생년월일_길이() throws Exception {
        // given
        String name = "testName";
        String password = "TestPassword123@";
        String phoneNumber = "01011112222";
        String nickname = "testNickname";
        String pin = "000000";
        String birthdate = "1999";

        MemberRegisterReq mRequest = MemberRegisterReq.builder()
                                                      .name(name)
                                                      .password(password)
                                                      .phoneNumber(phoneNumber)
                                                      .build();

        UserRegisterReq uRequest = UserRegisterReq.builder()
                                                  .pin(pin)
                                                  .birthdate(birthdate)
                                                  .nickname(nickname)
                                                  .memberRegisterReq(mRequest)
                                                  .build();

        // when & then
        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.post("/members/users/sign-up")
                                      .contentType("application/json")
                                      .content(objectMapper.writeValueAsString(uRequest))
        );

        actions.andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.resultCode").value(ERROR))
               .andExpect(jsonPath("$.code").value(BAD_REQUEST.name()))
               .andExpect(jsonPath("$.message").value(BAD_REQUEST.getMessage()))
               .andExpect(jsonPath("$.data").value("생년월일은 8자 입니다."))
               .andDo(print());
    }
}
