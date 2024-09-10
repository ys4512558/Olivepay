package kr.co.olivepay.franchise.dto.res;

import lombok.Builder;

/**
 * 다른 dto에 가맹점 데이터를 포함하는 경우를 위한 dto
 * @param id 가맹점 id
 * @param name 상호명
 */
@Builder
public record FranchiseMinimalRes (
	long id,
	String name
){
}
