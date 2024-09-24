package kr.co.olivepay.franchise.mapper;

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
	LikedFranchiseRes toLikedFranchiseRes(Like like);


}
