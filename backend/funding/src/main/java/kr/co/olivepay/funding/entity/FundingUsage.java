package kr.co.olivepay.funding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.co.olivepay.funding.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundingUsage extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="funding_usage_id", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long id;

	@Column(nullable = false)
	private String organization;

	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long amount;

	@Builder
	public FundingUsage(Long id, String organization, Long amount) {
		this.id = id;
		this.organization = organization;
		this.amount = amount;
	}
}
