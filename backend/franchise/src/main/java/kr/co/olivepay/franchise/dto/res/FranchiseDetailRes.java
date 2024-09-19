package kr.co.olivepay.franchise.dto.res;

import java.util.List;

import lombok.Builder;

/**
 * 가맹점 상세 검색(가맹점주)을 위한 dto
 */

@Builder
public class FranchiseDetailRes {
	Long id;
	String name;
	String category;
	String address;
	Integer coupon2;
	Integer coupon4;
	Integer likes;
}
