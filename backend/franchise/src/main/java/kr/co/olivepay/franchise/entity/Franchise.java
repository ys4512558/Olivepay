package kr.co.olivepay.franchise.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Franchise {

	@Id
	@Column(name="franchise_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	//가맹점주
	@Column(name = "owner_id")
	private Integer ownerId;

	//가맹점명
	private String name;

	//업종명
	private String category;;

	//전화번호
	private String telephoneNumber;

	//주소
	private String address;

	//사업자 등록 번호
	private String registrationNumber;

	@OneToMany(mappedBy = "franchise")
	private List<Review> reviews;

	@OneToMany(mappedBy = "franchise")
	private List<Like> likes;

}
