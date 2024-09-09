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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "franchise_like")
public class Like {

	@Id
	@Column(name="like_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//유저
	@Column(name = "user_id")
	private Integer userId;

	//가맹점
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "franchise_id")
	private Franchise franchise;

}
