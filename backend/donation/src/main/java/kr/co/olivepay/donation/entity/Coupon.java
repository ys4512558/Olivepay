package kr.co.olivepay.donation.entity;

import jakarta.persistence.*;
import kr.co.olivepay.donation.enums.CouponUnit;
import kr.co.olivepay.donation.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {
    @Id
    @Column(name = "coupon_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long franchiseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Donation donation;

    @Enumerated(value = EnumType.STRING)
    private CouponUnit couponUnit;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long count;

}
