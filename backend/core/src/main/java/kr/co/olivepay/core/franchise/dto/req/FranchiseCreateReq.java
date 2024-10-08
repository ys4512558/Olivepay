package kr.co.olivepay.core.franchise.dto.req;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kr.co.olivepay.core.franchise.validator.ValidCategory;
import lombok.Builder;

@Builder
public record FranchiseCreateReq(
	@NotBlank(message = "사업자 등록 번호는 필수 입력값입니다.")
	@Pattern(regexp = "^\\d{10}$", message = "사업자 등록 번호는 10자리 숫자여야 합니다.")
	String registrationNumber,

	@NotBlank(message = "상호명은 필수 입력값입니다.")
	@Size(max = 60, message = "상호명은 최대 60자까지 입력 가능합니다.")
	@Pattern(regexp = "^[가-힣a-zA-Z0-9\\s.,()\\[\\]&'-]*$",
		message = "상호명은 한글, 영문, 숫자, 공백 및 일부 특수문자(.,()[]&'-)만 입력 가능합니다.")
	String name,

	@NotBlank(message = "카테고리는 필수 입력값입니다.")
	@ValidCategory(message = "유효하지 않은 카테고리입니다.")
	String category,

	@NotBlank(message = "전화번호는 필수 입력값입니다.")
	@Pattern(regexp = "^\\d{9,11}$", message = "매장 전화번호는 9 ~ 11자리의 숫자여야 합니다.")
	String telephoneNumber,

	@NotBlank(message = "주소는 필수 입력값입니다.")
	@Size(max = 100, message = "주소는 최대 100자까지 입력 가능합니다.")
	String address,

	@NotNull(message = "위도는 필수 입력값입니다.")
	@DecimalMin(value = "30.0", message = "위도는 30도 이상이어야 합니다.")
	@DecimalMax(value = "45.0", message = "위도는 45도 이하여야 합니다.")
	Double latitude,

	@NotNull(message = "경도는 필수 입력값입니다.")
	@DecimalMin(value = "120.0", message = "경도는 120도 이상이어야 합니다.")
	@DecimalMax(value = "135.0", message = "경도는 135도 이하여야 합니다.")
	Double longitude

) {
}
