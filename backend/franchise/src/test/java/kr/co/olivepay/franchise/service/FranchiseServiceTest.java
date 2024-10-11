package kr.co.olivepay.franchise.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.co.olivepay.franchise.dto.req.FranchiseCreateReq;
import kr.co.olivepay.franchise.entity.Franchise;
import kr.co.olivepay.franchise.global.enums.ErrorCode;
import kr.co.olivepay.franchise.global.enums.NoneResponse;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.handler.AppException;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.mapper.FranchiseMapper;
import kr.co.olivepay.franchise.repository.FranchiseRepository;
import kr.co.olivepay.franchise.service.impl.FranchiseServiceImpl;

@ExtendWith(SpringExtension.class)
public class FranchiseServiceTest {

	@Mock
	private FranchiseMapper franchiseMapper;
	@Mock
	private FranchiseRepository franchiseRepository;

	@InjectMocks
	private FranchiseServiceImpl franchiseService;

	@Test
	@DisplayName("가맹점 등록 성공")
	void 가맹점_등록_성공() {
		// Given
		Long memberId = 1L;
		String registrationNumber = "123456789";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("테스트 가맹점")
													   .category("OTHER")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		Franchise franchise = Franchise.builder()
									   .memberId(1L)
									   .registrationNumber(registrationNumber)
									   .name("테스트 가맹점")
									   .category("OTHER")
									   .telephoneNumber("0212345678")
									   .address("서울시 강남구")
									   .latitude(37.5665F)
									   .longitude(126.9780F)
									   .build();

		when(franchiseRepository.existsByRegistrationNumber(registrationNumber)).thenReturn(false);
		when(franchiseMapper.toEntity(eq(memberId), any(FranchiseCreateReq.class))).thenReturn(franchise);
		when(franchiseRepository.save(any(Franchise.class))).thenReturn(franchise);

		// When
		SuccessResponse<NoneResponse> response = franchiseService.registerFranchise(memberId, request);

		// Then
		assertNotNull(response);
		assertEquals(SuccessCode.FRANCHISE_REGISTER_SUCCESS, response.successCode());

		// Verify
		verify(franchiseRepository).existsByRegistrationNumber(registrationNumber);
		verify(franchiseMapper).toEntity(eq(memberId), any(FranchiseCreateReq.class));
		verify(franchiseRepository).save(any(Franchise.class));
	}

	@Test
	@DisplayName("가맹점 등록 실패 - 사업자등록번호 중복")
	void 가맹점_등록_실패_사업자등록번호_중복() {
		// Given
		Long memberId = 1L;
		String registrationNumber = "123456789";
		FranchiseCreateReq request = FranchiseCreateReq.builder()
													   .registrationNumber(registrationNumber)
													   .name("테스트 가맹점")
													   .category("OTHER")
													   .telephoneNumber("0212345678")
													   .address("서울시 강남구")
													   .latitude(37.5665F)
													   .longitude(126.9780F)
													   .build();

		when(franchiseRepository.existsByRegistrationNumber(registrationNumber)).thenReturn(true);

		// When
		AppException exception = assertThrows(AppException.class, () -> franchiseService.registerFranchise(memberId, request));

		// Then
		assertEquals(ErrorCode.FRANCHISE_REGISTRATION_NUMBER_DUPLICATED, exception.getErrorCode());

		// Verify
		verify(franchiseRepository).existsByRegistrationNumber(registrationNumber);
		verifyNoInteractions(franchiseMapper);
		verify(franchiseRepository, never()).save(any(Franchise.class));
	}


}
