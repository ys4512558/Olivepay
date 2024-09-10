package kr.co.olivepay.franchise.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ReviewCreateReq(
	@NotNull(message = "가맹점 ID는 필수입니다.")
	@Positive(message = "가맹점 ID는 양수여야 합니다.")
	Long franchiseId,

	@NotNull(message = "별점은 필수입니다.")
	@Min(value = 1, message = "별점의 최소값은 1점 입니다.")
	@Max(value = 5, message = "별점은 최대값은 5점 입니다.")
	Integer stars,

	@NotBlank(message = "내용은 공백일 수 없습니다.")
	@Size(max = 255, message = "내용은 최대 255자까지 입력 가능합니다.")
	String content
) {
}
