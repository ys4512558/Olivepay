package kr.co.olivepay.franchise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.olivepay.franchise.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "franchise_like")
public class Like extends BaseEntity  {

	@Id
	@Column(name="like_id", nullable = false, columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//유저
	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long memberId;

	//가맹점
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "franchise_id", nullable = false, columnDefinition = "INT UNSIGNED")
	private Franchise franchise;


	@Builder
	public Like(Long memberId, Franchise franchise) {
		this.memberId = memberId;
		this.franchise = franchise;
	}
}
