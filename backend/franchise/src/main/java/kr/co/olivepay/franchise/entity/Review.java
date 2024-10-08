package kr.co.olivepay.franchise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.co.olivepay.franchise.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

	@Id
	@Column(name="review_id", nullable = false, columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//유저
	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long memberId;

	//가맹점
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "franchise_id", nullable = false, columnDefinition = "INT UNSIGNED")
	private Franchise franchise;

	//결제내역 ID
	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long paymentId;

	//내용
	@Column(nullable = false)
	private String content;

	//평점
	private Integer stars;

	@Builder
	public Review (Long memberId, Franchise franchise, Long paymentId, String content, Integer stars) {
			this.memberId = memberId;
			this.franchise = franchise;
			this.paymentId=paymentId;
			this.content = content;
			this.stars = stars;
		}

}
