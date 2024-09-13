package kr.co.olivepay.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {

    @Id
    @Column(name = "owner_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Member member;

    @Column(nullable = false, length = 6)
    private String rrnPrefix;

    @Column(nullable = false)
    private Byte rrnCheckDigit;

    @Builder
    public Owner(Member member, String rrnPrefix, Byte rrnCheckDigit){
        this.member = member;
        this.rrnPrefix = rrnPrefix;
        this.rrnCheckDigit = rrnCheckDigit;
    }
}

