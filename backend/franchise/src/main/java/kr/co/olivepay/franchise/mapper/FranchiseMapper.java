package kr.co.olivepay.franchise.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import kr.co.olivepay.franchise.controller.FranchiseController;
import kr.co.olivepay.franchise.dto.req.*;
import kr.co.olivepay.franchise.dto.res.*;
import kr.co.olivepay.franchise.entity.*;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

	@Mapping(source = "franchiseReq.category", target = "category", qualifiedByName = "stringToCategory")
	Franchise toEntity(Long memberId, FranchiseCreateReq franchiseReq);

	@Mapping(source = "franchise.id", target = "franchiseId")
	@Mapping(source = "franchise.name", target = "franchiseName")
	FranchiseDetailRes toFranchiseDetailRes(Franchise franchise, Integer coupon2, Integer coupon4, Integer likes, Boolean isLiked);

	ExistenceRes toExistenceRes(Boolean isExist);

	FranchiseMinimalRes toFranchiseMinimalRes(Franchise franchise);

	@Mapping(source = "franchise.id", target = "franchiseId")
	@Mapping(source = "franchise.name", target = "franchiseName")
	FranchiseBasicRes toFranchiseBasicRes(Franchise franchise, Integer likes, Integer coupons, Float avgStars);

	QrCodeRes toQrCodeRes(String image);

	@Named("stringToCategory")
	default Category stringToCategory(String category) {
		return Category.fromString(category);
	}
}