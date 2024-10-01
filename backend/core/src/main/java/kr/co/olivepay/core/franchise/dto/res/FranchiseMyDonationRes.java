package kr.co.olivepay.core.franchise.dto.res;

import lombok.Builder;

@Builder
public record FranchiseMyDonationRes(
        Long franchiseId,
        String name,
        String address
) { }
