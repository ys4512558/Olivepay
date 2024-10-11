package kr.co.olivepay.franchise.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import kr.co.olivepay.franchise.entity.Category;
import kr.co.olivepay.franchise.entity.Franchise;
import kr.co.olivepay.franchise.repository.FranchiseRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FranchiseRepositoryCustomImpl implements FranchiseRepositoryCustom {

	private final EntityManager entityManager;

	@Override
	public List<Franchise> findByLocationAndCategory(Double latitude, Double longitude,
		Double latRange, Double lonRange, Category category) {

		double minLat = latitude - latRange;
		double maxLat = latitude + latRange;
		double minLon = longitude - lonRange;
		double maxLon = longitude + lonRange;

		log.debug("Search bounds: minLat={}, minLon={}, maxLat={}, maxLon={}", minLat, minLon, maxLat, maxLon);

		StringBuilder sql = new StringBuilder(
			"SELECT f.* FROM franchise f WHERE " +
				"MBRContains(ST_SRID(ST_GeomFromText(CONCAT(" +
				"'POLYGON((', :minLon, ' ', :minLat, ',', :maxLon, ' ', :minLat, ',' , " +
				":maxLon, ' ', :maxLat, ',', :minLon, ' ', :maxLat, ',', :minLon, ' ', :minLat, '))')), 4326), " +
				"f.location)"
		);

		if (category != null) {
			sql.append(" AND f.category = :category");
		}

		Query query = entityManager.createNativeQuery(sql.toString(), Franchise.class);
		query.setParameter("minLat", minLat);
		query.setParameter("minLon", minLon);
		query.setParameter("maxLat", maxLat);
		query.setParameter("maxLon", maxLon);

		if (category != null) {
			query.setParameter("category", category.name());
		}

		return query.getResultList();
	}

}
