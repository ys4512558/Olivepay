package kr.co.olivepay.member.controller;

import kr.co.olivepay.member.dto.req.MemberRegisterReq;
import kr.co.olivepay.member.dto.req.UserRegisterReq;
import kr.co.olivepay.member.global.enums.NoneResponse;
import kr.co.olivepay.member.global.response.Response;
import kr.co.olivepay.member.global.response.SuccessResponse;
import kr.co.olivepay.member.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static kr.co.olivepay.member.global.enums.SuccessCode.USER_CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

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

        SuccessResponse<NoneResponse> expectedResponse =
                new SuccessResponse<>(USER_CREATED, NoneResponse.NONE);


        // when
        when(userService.registerUser(uRequest)).thenReturn(expectedResponse);

        // then
        ResponseEntity<Response<NoneResponse>> responseEntity = userController.registerUser(uRequest);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getStatusCode()).isEqualTo(USER_CREATED.getHttpStatus());

        // verify
        verify(userService).registerUser(uRequest);
    }
}
