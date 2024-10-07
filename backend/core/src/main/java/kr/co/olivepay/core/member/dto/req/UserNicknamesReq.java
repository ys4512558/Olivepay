package kr.co.olivepay.core.member.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.List;

@Builder
public record UserNicknamesReq(
        @Valid
        List<@Positive Long> memberIds
) { }
