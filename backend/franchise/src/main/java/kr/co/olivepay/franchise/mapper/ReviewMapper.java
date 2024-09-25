package kr.co.olivepay.franchise.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import kr.co.olivepay.franchise.dto.req.ReviewCreateReq;
import kr.co.olivepay.franchise.dto.res.EmptyReviewRes;
import kr.co.olivepay.franchise.dto.res.FranchiseReviewRes;
import kr.co.olivepay.franchise.dto.res.PagedFranchiseReviewsRes;
import kr.co.olivepay.franchise.dto.res.UserReviewRes;
import kr.co.olivepay.franchise.entity.Franchise;
import kr.co.olivepay.franchise.entity.Review;
import kr.co.olivepay.franchise.repository.FranchiseRepository;

@Mapper(componentModel = "spring", uses = {FranchiseRepository.class})
public interface ReviewMapper {

	@Mapping(source = "memberId", target = "memberId")
	@Mapping(source = "reviewReq.content", target = "content")
	@Mapping(source = "reviewReq.stars", target = "stars")
	@Mapping(target = "franchise", expression = "java(franchiseRepository.getById(reviewReq.franchiseId()))")
	Review toEntity(Long memberId, ReviewCreateReq reviewReq, @Context FranchiseRepository franchiseRepository);

	@Mapping(source = "id", target = "reviewId")
	FranchiseReviewRes toFranchiseReviewRes(Review review);

	UserReviewRes toUserReviewRes(Review review);
	EmptyReviewRes toEmptyReviewRes(Review review);

	PagedFranchiseReviewsRes toPagedFranchiseReviewRes(Long nextIndex, List<FranchiseReviewRes> reviews);
}
