package kr.co.olivepay.funding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Funding {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="funding_id", nullable = false, columnDefinition = "INT UNSIGNED")
	private Long id;

	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long couponUserId;

	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long amount;

	@Builder
	public Funding(Long id, Long couponUserId, Long amount) {
		this.id = id;
		this.couponUserId = couponUserId;
		this.amount = amount;
	}

}
