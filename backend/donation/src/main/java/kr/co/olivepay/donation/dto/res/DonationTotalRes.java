package kr.co.olivepay.donation.dto.res;

import lombok.Builder;

@Builder
public record DonationTotalRes(
        Long mealCount,
        Long total
) { }
