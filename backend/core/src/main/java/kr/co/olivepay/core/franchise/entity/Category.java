package kr.co.olivepay.core.franchise.entity;

public enum Category {

	GENERAL, //일반대중음식점
	WESTERN, //양식
	JAPANESE, //일식
	BAKERY, //제과점
	CHINESE, //중식
	FASTFOOD, //패스트푸드
	KOREAN, //한식
	SUPERMARKET, //할인점/슈퍼마켓
	OTHER; //기타

	public static Category fromString(String text) {
		for (Category c : Category.values()) {
			if (c.name().equalsIgnoreCase(text)) {
				return c;
			}
		}
		return Category.OTHER;
	}

}
