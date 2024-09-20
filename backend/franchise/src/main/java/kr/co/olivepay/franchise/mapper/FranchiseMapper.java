package kr.co.olivepay.franchise.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import kr.co.olivepay.franchise.dto.req.FranchiseCreateReq;
import kr.co.olivepay.franchise.entity.Category;
import kr.co.olivepay.franchise.entity.Franchise;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

	@Mapping(source = "franchiseReq.category", target = "category", qualifiedByName = "stringToCategory")
	@Mapping(source = "franchiseReq.latitude", target = "latitude", qualifiedByName = "floatToBigDecimal")
	@Mapping(source = "franchiseReq.longitude", target = "longitude", qualifiedByName = "floatToBigDecimal")
	Franchise toEntity(Long memberId, FranchiseCreateReq franchiseReq);


	@Named("stringToCategory")
	default Category stringToCategory(String category) {
		return Category.fromString(category);
	}

	@Named("floatToBigDecimal")
	default BigDecimal floatToBigDecimal(Float value) {
		if (value == null) {
			return null;
		}
		return new BigDecimal(value.toString()).setScale(8, RoundingMode.HALF_UP);
	}
}