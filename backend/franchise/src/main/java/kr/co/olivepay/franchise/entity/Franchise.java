package kr.co.olivepay.franchise.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import kr.co.olivepay.franchise.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Franchise extends BaseEntity {

	@Id
	@Column(name="franchise_id", nullable = false, columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//가맹점주
	@Column(nullable = false, columnDefinition = "INT UNSIGNED")
	private Long memberId;

	//가맹점명
	@Column(nullable = false, length = 20)
	private String name;

	//업종명
	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private Category category;

	//전화번호
	@Column(nullable = false, length = 12)
	private String telephoneNumber;

	//주소
	@Column(nullable = false, length = 50)
	private String address;

	//사업자 등록 번호
	@Column(nullable = false, length = 12)
	private String registrationNumber;


	@Builder
	public Franchise(Long memberId, String name, String category, String telephoneNumber, String address, String registrationNumber){
		this.memberId = memberId;
		this.name = name;
		this.category = Category.fromString(category);
		this.telephoneNumber = telephoneNumber;
		this.address = address;
		this.registrationNumber = registrationNumber;
	}

}
