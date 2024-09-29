package kr.co.olivepay.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import kr.co.olivepay.core.franchise.dto.req.FranchiseCreateReq;
import lombok.Builder;

@Builder
public record OwnerRegisterReq (

        @Valid
        MemberRegisterReq memberRegisterReq,

        @Valid
        FranchiseCreateReq franchiseCreateReq,

        @Schema(example = "890830")
        @Pattern(
                regexp = "^\\d{6}$",
                message = "주민번호 앞자리는 6자리의 숫자여야 합니다."
        )
        String rrnPrefix,

        @Schema(example = "1")
        @Min(value = 1, message = "주민번호 뒷자리는 1부터 4 사이의 숫자여야 합니다.")
        @Max(value = 4, message = "주민번호 뒷자리는 1부터 4 사이의 숫자여야 합니다.")
        Integer rrnCheckDigit
){ }
