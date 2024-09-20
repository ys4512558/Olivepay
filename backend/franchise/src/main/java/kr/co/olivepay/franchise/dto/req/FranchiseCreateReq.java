package kr.co.olivepay.franchise.dto.req;

import jakarta.validation.constraints.NotBlank;
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
	String address
) {
}
