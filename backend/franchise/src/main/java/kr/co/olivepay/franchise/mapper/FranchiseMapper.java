package kr.co.olivepay.franchise.mapper;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import kr.co.olivepay.core.donation.dto.req.CouponListReq;
import kr.co.olivepay.core.franchise.dto.res.FranchiseMyDonationRes;
import kr.co.olivepay.franchise.dto.req.*;
import kr.co.olivepay.franchise.dto.res.*;
import kr.co.olivepay.franchise.entity.*;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

	@Mapping(target = "location", expression = "java(coordinatesToPoint(franchiseReq.latitude(), franchiseReq.longitude()))")
	@Mapping(source = "franchiseReq.category", target = "category", qualifiedByName = "stringToCategory")
	Franchise toEntity(Long memberId, FranchiseCreateReq franchiseReq);

	@Mapping(source = "franchise.id", target = "franchiseId")
	@Mapping(source = "franchise.name", target = "franchiseName")
	FranchiseDetailRes toFranchiseDetailRes(Franchise franchise, Long coupon2, Long coupon4, Long likes, Boolean isLiked, Long reviews);

	ExistenceRes toExistenceRes(Boolean isExist);

	FranchiseMinimalRes toFranchiseMinimalRes(Franchise franchise);

	@Mapping(source = "franchise.id", target = "franchiseId")
	@Mapping(source = "franchise.name", target = "franchiseName")
	FranchiseBasicRes toFranchiseBasicRes(Franchise franchise, Long likes, Long coupons, Float avgStars);

	@Mapping(source = "id", target="franchiseId")
	FranchiseMyDonationRes toFranchiseMyDonationRes(Franchise franchise);

	List<FranchiseMyDonationRes> toFranchiseMyDonationResList(List<Franchise> franchiseList);

	QrCodeRes toQrCodeRes(String image);

	default CouponListReq toCouponListReq(List<Long> franchiseIdList) {
		return CouponListReq.builder()
							.franchiseIdList(franchiseIdList)
							.build();
	}

	@Named("stringToCategory")
	default Category stringToCategory(String category) {
		return Category.fromString(category);
	}

	@Named("coordinatesToPoint")
	default Point coordinatesToPoint(Double latitude, Double longitude) {
		GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
		return geometryFactory.createPoint(new Coordinate(longitude, latitude));
	}
}