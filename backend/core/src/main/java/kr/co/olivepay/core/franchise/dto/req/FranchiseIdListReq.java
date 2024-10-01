package kr.co.olivepay.core.franchise.dto.req;

import lombok.Builder;

import java.util.List;

@Builder
public record FranchiseIdListReq(
        List<Long> franchiseIdList
) {
}
