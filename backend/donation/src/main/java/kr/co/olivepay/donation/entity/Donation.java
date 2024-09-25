package kr.co.olivepay.donation.entity;


import jakarta.persistence.*;
import kr.co.olivepay.donation.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Donation extends BaseEntity {
    @Id
    @Column(name = "donation_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long franchiseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Donor donor;

    private Integer money;

    @Builder
    public Donation(Long franchiseId, Donor donor, Integer money) {
        this.franchiseId = franchiseId;
        this.donor = donor;
        this.money = money;
    }
}
