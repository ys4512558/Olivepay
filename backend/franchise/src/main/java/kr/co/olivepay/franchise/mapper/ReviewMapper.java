package kr.co.olivepay.franchise.mapper;

import org.mapstruct.Mapper;

import kr.co.olivepay.franchise.dto.req.ReviewCreateReq;
import kr.co.olivepay.franchise.dto.res.EmptyReviewRes;
import kr.co.olivepay.franchise.dto.res.FranchiseReviewRes;
import kr.co.olivepay.franchise.dto.res.UserReviewRes;
import kr.co.olivepay.franchise.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

	Review toEntity(ReviewCreateReq reviewReq);
	FranchiseReviewRes toFranchiseReviewRes(Review review);
	UserReviewRes toUserReviewRes(Review review);
	EmptyReviewRes toEmptyReviewRes(Review review);

}
