package kr.co.olivepay.franchise.dto.req;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record FranchiseCreateReq(
	@NotBlank(message = "사업자 등록 번호는 공백일 수 없습니다.")
	@Size(max = 12, message = "사업자 등록 번호는 최대 12자까지 입력 가능합니다.")
	String registrationNumber,

	@NotBlank(message = "상호명은 공백일 수 없습니다.")
	@Size(max = 20, message = "상호명은 최대 20자까지 입력 가능합니다.")
	String name,

	@NotBlank(message = "카테고리는 공백일 수 없습니다.")
	@Size(max = 10, message = "카테고리는 최대 10자까지 입력 가능합니다.")
	String category,

	@NotBlank(message = "전화번호는 공백일 수 없습니다.")
	@Size(max = 12, message = "전화번호는 최대 12자까지 입력 가능합니다.")
	String telephoneNumber,

	@NotBlank(message = "주소는 공백일 수 없습니다.")
	@Size(max = 50, message = "주소는 최대 50자까지 입력 가능합니다.")
	String address,

	@NotNull(message = "위도는 필수 입력값입니다.")
	@DecimalMin(value = "-90.0", message = "위도는 -90.0 이상이어야 합니다.")
	@DecimalMax(value = "90.0", message = "위도는 90.0 이하여야 합니다.")
	Float latitude,

	@NotNull(message = "경도는 필수 입력값입니다.")
	@DecimalMin(value = "-180.0", message = "경도는 -180.0 이상이어야 합니다.")
	@DecimalMax(value = "180.0", message = "경도는 180.0 이하여야 합니다.")
	Float longitude

) {
}
