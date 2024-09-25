package kr.co.olivepay.franchise.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kr.co.olivepay.franchise.entity.Franchise;
import kr.co.olivepay.franchise.dto.res.LikedFranchiseRes;
import kr.co.olivepay.franchise.entity.Like;

@Mapper(componentModel = "spring")
public interface LikeMapper {

	@Mapping(source = "franchise", target="franchise")
	Like toEntity(Long memberId, Franchise franchise);

	@Mapping(source = "like.id", target = "likeId")
	@Mapping(source="like.franchise.id", target = "franchiseId")
	@Mapping(source="like.franchise.name", target = "franchiseName")
	@Mapping(source="like.franchise.address", target = "address")
	@Mapping(source="like.franchise.latitude", target = "latitude")
	@Mapping(source="like.franchise.longitude", target = "longitude")
	@Mapping(source="like.franchise.category", target = "category")
	LikedFranchiseRes toLikedFranchiseRes(Like like);

	List<LikedFranchiseRes> toLikedFranchiseResList(List<Like> likes);

}
