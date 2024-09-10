package kr.co.olivepay.franchise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

	@Id
	@Column(name="review_id", nullable = false, columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//유저
	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long userId;

	//가맹점
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "franchise_id", nullable = false, columnDefinition = "INT UNSIGNED")
	private Franchise franchise;

	//내용
	@Column(nullable = false)
	private String content;

	//평점
	private Integer stars;

	@Builder
	public Review (Long userId, Franchise franchise, String content, Integer stars) {
		this.userId = userId;
		this.franchise = franchise;
		this.content = content;
		this.stars = stars;
	}

}
