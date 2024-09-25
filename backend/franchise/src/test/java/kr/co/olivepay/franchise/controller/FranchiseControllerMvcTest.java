package kr.co.olivepay.franchise.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import kr.co.olivepay.franchise.dto.req.FranchiseCreateReq;
import kr.co.olivepay.franchise.global.enums.ErrorCode;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.service.FranchiseService;

@WebMvcTest(FranchiseController.class)
@TestPropertySource(properties = {"server.port=8104"})
@AutoConfigureMockMvc(addFilters = false)
public class FranchiseControllerMvcTest {

	@MockBean
	private FranchiseService franchiseService;

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("가맹점 등록 성공")
	void 가맹점_등록_성공() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		SuccessResponse<NoneResponse> response = new SuccessResponse<>(SuccessCode.FRANCHISE_REGISTER_SUCCESS,
			NoneResponse.NONE);

		//when
		when(franchiseService.registerFranchise(memberId, request)).thenReturn(response);

		//then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isCreated())
			   .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
			   .andExpect(jsonPath("$.code").value(SuccessCode.FRANCHISE_REGISTER_SUCCESS.name()))
			   .andExpect(jsonPath("$.message").value(SuccessCode.FRANCHISE_REGISTER_SUCCESS.getMessage()));
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 사업자 등록 번호 공백")
	void 가맹점_등록_실패_사업자등록번호_누락() throws Exception {
		//given
		Long memberId = 1L;
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .name("역삼정")
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		//when
		//then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("사업자 등록 번호는 필수 입력값입니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 사업자 등록 번호 길이 오류")
	void 가맹점_등록_실패_사업자등록번호_길이_오류() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "12345";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("사업자 등록 번호는 10자리 숫자여야 합니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 상호명 공백")
	void 가맹점_등록_실패_상호명_공백() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("상호명은 필수 입력값입니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 상호명 특수 기호 포함")
	void 가맹점_등록_실패_상호명_특수기호_포함() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("**역삼정**")
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("상호명은 한글, 영문, 숫자, 공백, 느낌표(!), 물음표(?)만 입력 가능합니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 카테고리 공백")
	void 가맹점_등록_실패_카테고리_공백() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("카테고리는 필수 입력값입니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 유효하지 않은 카테고리")
	void 가맹점_등록_실패_유효하지_않은_카테고리() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("HACHUPING")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("유효하지 않은 카테고리입니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 전화번호 공백")
	void 가맹점_등록_실패_전화번호_공백() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("KOREAN")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("전화번호는 필수 입력값입니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 전화번호 길이 오류")
	void 가맹점_등록_실패_전화번호_길이_오류() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("KOREAN")
													   .telephoneNumber("0212345")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("전화번호는 10자리 또는 11자리의 숫자여야 합니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 주소 공백")
	void 가맹점_등록_실패_주소_공백() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("주소는 필수 입력값입니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 주소 길이 초과")
	void 가맹점_등록_실패_주소_길이_초과() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구 역삼 멀티캠퍼스 밥 존맛임. 그중에서도 10층 도시락은 가히 야무지다고 할 수 있다.")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("주소는 최대 50자까지 입력 가능합니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 위도 공백")
	void 가맹점_등록_실패_위도_공백() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("위도는 필수 입력값입니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 위도 범위 초과")
	void 가맹점_등록_실패_위도_범위_초과() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(12.5665F)
													   .longitude(126.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("위도는 30도 이상이어야 합니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 경도 누락")
	void 가맹점_등록_실패_경도_누락() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("경도는 필수 입력값입니다."))
			   .andDo(print());
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 경도 범위 초과")
	void 가맹점_등록_실패_경도_범위_초과() throws Exception {
		//given
		Long memberId = 1L;
		String registrationNumber = "1234567890";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("역삼정")
													   .category("KOREAN")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(145.9780F)
													   .build();

		//when & then
		ResultActions actions = mockMvc.perform(
			MockMvcRequestBuilders.post("/franchises/owner")
								  .contentType("application/json")
								  .content(objectMapper.writeValueAsString(request))
		);

		actions.andExpect(status().isBadRequest())
			   .andExpect(jsonPath("$.resultCode").value("ERROR"))
			   .andExpect(jsonPath("$.code").value(ErrorCode.BAD_REQUEST.name()))
			   .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
			   .andExpect(jsonPath("$.data").value("경도는 135도 이하여야 합니다."))
			   .andDo(print());
	}

}
