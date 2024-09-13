package kr.co.olivepay.card.entity;

import jakarta.persistence.*;
import kr.co.olivepay.card.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardCompany extends BaseEntity {

    @Id
    @Column(name = "card_company_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Builder
    public CardCompany(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
