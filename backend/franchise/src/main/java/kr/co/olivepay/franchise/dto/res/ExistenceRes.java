package kr.co.olivepay.franchise.dto.res;

import lombok.Builder;

/**
 * 사업자등록번호 중복 확인 결과를 위한 dto
 * @param isExist
 */
@Builder
public record ExistenceRes(
	Boolean isExist
) {
}
