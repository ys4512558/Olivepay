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
public class CouponUser extends BaseEntity {
    @Id
    @Column(name = "coupon_user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Coupon coupon;

    @Column(nullable = false)
    private Boolean isUsed = false;

    @Builder
    public CouponUser(Long memberId, Coupon coupon, Boolean isUsed) {
        this.memberId = memberId;
        this.coupon = coupon;
        this.isUsed = isUsed;
    }

    public void updateIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }
}
