package kr.co.olivepay.franchise.repository;

import java.util.List;

import kr.co.olivepay.franchise.entity.Category;
import kr.co.olivepay.franchise.entity.Franchise;

public interface FranchiseRepositoryCustom {

	List<Franchise> findByLocationAndCategory(Double latitude, Double longitude,
		Double latRange, Double lonRange, Category category);
}

