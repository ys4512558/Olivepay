package kr.co.olivepay.franchise.mapper;

import org.mapstruct.Mapper;

import kr.co.olivepay.franchise.entity.Like;

@Mapper(componentModel = "spring")
public interface LikeMapper {

	Like toEntity(Long memberId, Long franchiseId);

}
