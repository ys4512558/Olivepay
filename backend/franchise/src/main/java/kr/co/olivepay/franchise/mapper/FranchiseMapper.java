package kr.co.olivepay.franchise.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import kr.co.olivepay.franchise.dto.req.FranchiseCreateReq;
import kr.co.olivepay.franchise.dto.res.QrCodeRes;
import kr.co.olivepay.franchise.entity.Category;
import kr.co.olivepay.franchise.entity.Franchise;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

	@Mapping(source = "franchiseReq.category", target = "category", qualifiedByName = "stringToCategory")
	Franchise toEntity(Long memberId, FranchiseCreateReq franchiseReq);

	QrCodeRes toQrCodeRes(String image);

	@Named("stringToCategory")
	default Category stringToCategory(String category) {
		return Category.fromString(category);
	}
}