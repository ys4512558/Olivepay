package kr.co.olivepay.member.service;


import kr.co.olivepay.member.dto.req.MemberRegisterReq;
import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.entity.Member;
import kr.co.olivepay.member.entity.User;
import kr.co.olivepay.member.enums.Role;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.handler.AppException;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.mapper.UserMapper;
import kr.co.olivepay.member.openapi.dto.res.member.MemberCreateAndSearchRes;
import kr.co.olivepay.member.openapi.service.FintechService;
import kr.co.olivepay.member.repository.UserRepository;
import kr.co.olivepay.member.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static kr.co.olivepay.member.global.enums.ErrorCode.*;
import static kr.co.olivepay.member.global.enums.SuccessCode.USER_CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private MemberService memberService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private FintechService fintechService;

    @Mock
    private MemberCreateAndSearchRes openApiMock;
    private String EMAIL_SUFFIX = "@ssafy.co.kr";

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("유저 회원가입 성공")
    void 유저_회원가입_성공() throws Exception {
        // given
        String name = "testName";
        String password = "testPassword";
        String phoneNumber = "01011112222";
        String nickname = "testNickname";
        String pin = "000000";
        String birthdate = "19990303";
        Role role = Role.TEMP_USER;
        String userKey = "hashedUserKey";

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

        Member savedMember = Member.builder().build();

        User expectedUser = User.builder()
                                .nickname(nickname)
                                .pin(pin)
                                .birthdate(birthdate)
                                .userKey(userKey)
                                .member(savedMember)
                                .build();

        // when
        when(memberService.registerMember(mRequest, role)).thenReturn(savedMember);
        when(openApiMock.getUserKey()).thenReturn(userKey);
        when(fintechService.createMember(phoneNumber+EMAIL_SUFFIX))
                .thenReturn(openApiMock);
        when(userMapper.toUser(uRequest, savedMember, openApiMock.getUserKey()))
                .thenReturn(expectedUser);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        // then
        SuccessResponse<NoneResponse> response = userService.registerUser(uRequest);

        assertThat(response).isNotNull();
        assertThat(response.successCode()).isEqualTo(USER_CREATED);

        // verify
        verify(memberService).registerMember(mRequest, role);
        verify(openApiMock, atLeast(1)).getUserKey();
        verify(fintechService).createMember(phoneNumber + EMAIL_SUFFIX);
        verify(userMapper).toUser(uRequest, savedMember, userKey);
        verify(userRepository).save(expectedUser);
    }

    @Test
    @DisplayName("유저 회원가입 성공 - 금융망 API 이미 존재하는 Member")
    void 유저_회원가입_성공_금융망_API_이미_존재하는_Member() throws Exception {
        // given
        String name = "testName";
        String password = "testPassword";
        String phoneNumber = "01011112222";
        String nickname = "testNickname";
        String pin = "000000";
        String birthdate = "19990303";
        Role role = Role.TEMP_USER;
        String userKey = "hashedUserKey";

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

        Member savedMember = Member.builder().build();

        User expectedUser = User.builder()
                                .nickname(nickname)
                                .pin(pin)
                                .birthdate(birthdate)
                                .userKey(userKey)
                                .member(savedMember)
                                .build();

        // when
        when(memberService.registerMember(mRequest, role)).thenReturn(savedMember);
        when(openApiMock.getUserKey()).thenReturn(userKey);
        when(fintechService.createMember(phoneNumber+EMAIL_SUFFIX))
                .thenThrow(new AppException(FINTECH_API_ID_ALREADY_EXISTS));
        when(fintechService.searchMember(phoneNumber+EMAIL_SUFFIX))
                .thenReturn(openApiMock);
        when(userMapper.toUser(uRequest, savedMember, openApiMock.getUserKey()))
                .thenReturn(expectedUser);
        when(userRepository.save(expectedUser)).thenReturn(expectedUser);

        // then
        SuccessResponse<NoneResponse> response = userService.registerUser(uRequest);

        assertThat(response).isNotNull();
        assertThat(response.successCode()).isEqualTo(USER_CREATED);

        // verify
        verify(memberService).registerMember(mRequest, role);
        verify(openApiMock, atLeast(1)).getUserKey();
        verify(fintechService).createMember(phoneNumber + EMAIL_SUFFIX);
        verify(fintechService).searchMember(phoneNumber + EMAIL_SUFFIX);
        verify(userMapper).toUser(uRequest, savedMember, userKey);
        verify(userRepository).save(expectedUser);
    }

    @Test
    @DisplayName("유저 회원가입 실패- 금융망 API 내부 오류")
    void 유저_회원가입_실패_금융망_API_내부_오류() throws Exception {
        // given
        String name = "testName";
        String password = "testPassword";
        String phoneNumber = "01011112222";
        String nickname = "testNickname";
        String pin = "000000";
        String birthdate = "19990303";
        Role role = Role.TEMP_USER;

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


        // when
        when(fintechService.createMember(phoneNumber+EMAIL_SUFFIX))
                .thenThrow(new AppException(FINTECH_API_REQUEST));


        // then
        Throwable thrown = catchThrowable(() -> userService.registerUser(uRequest));

        assertThat(thrown)
                .isInstanceOf(AppException.class)
                .hasFieldOrPropertyWithValue("errorCode", FINTECH_API_REQUEST);

        // verify
        verify(openApiMock, never()).getUserKey();
        verify(fintechService).createMember(phoneNumber + EMAIL_SUFFIX);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("유저 회원가입 실패 - 중복 회원")
    void 유저_회원가입_실패_중복_회원() throws Exception {
        // given
        String name = "testName";
        String password = "testPassword";
        String phoneNumber = "01011112222";
        String nickname = "testNickname";
        String pin = "000000";
        String birthdate = "19990303";
        Role role = Role.TEMP_USER;
        String userKey = "hashedUserKey";

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

        Member savedMember = Member.builder().build();

        // when
        when(memberService.registerMember(mRequest, role)).thenReturn(savedMember);
        when(openApiMock.getUserKey()).thenReturn(userKey);
        when(fintechService.createMember(phoneNumber+EMAIL_SUFFIX))
                .thenReturn(openApiMock);
        when(memberService.registerMember(mRequest, role))
                .thenThrow(new AppException(PHONE_NUMBER_DUPLICATED));


        // then
        Throwable thrown = catchThrowable(() -> userService.registerUser(uRequest));

        assertThat(thrown)
                .isInstanceOf(AppException.class)
                .hasFieldOrPropertyWithValue("errorCode", PHONE_NUMBER_DUPLICATED);

        // verify
        verify(memberService).registerMember(mRequest, role);
        verify(openApiMock, atLeast(1)).getUserKey();
        verify(fintechService).createMember(phoneNumber + EMAIL_SUFFIX);
        verify(userRepository, never()).save(any());
    }
}
