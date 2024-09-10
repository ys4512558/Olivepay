package kr.co.olivepay.card.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {

    @Id
    @Column(name = "card_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_company_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private CardCompany cardCompany;

    @Column(nullable = false, length = 16)
    private String cardNumber;

    @Column(nullable = false, length = 2)
    private String expirationYear;

    @Column(nullable = false, length = 2)
    private String expirationMonth;

    @Column(nullable = false, length = 3)
    private String cvc;

    @Column(nullable = false, length = 2)
    private String creditPassword;

    @Column(nullable = false)
    private Boolean isDefault;

    @Column(nullable = false, length = 16)
    private String realCardNumber;

    @Builder
    public Card(
            Long id, Long userId, Account account, CardCompany cardCompany, String cardNumber, String expirationYear,
            String expirationMonth, String cvc, String creditPassword, Boolean isDefault, String realCardNumber
    ) {
        this.id = id;
        this.userId = userId;
        this.account = account;
        this.cardCompany = cardCompany;
        this.cardNumber = cardNumber;
        this.expirationYear = expirationYear;
        this.expirationMonth = expirationMonth;
        this.cvc = cvc;
        this.creditPassword = creditPassword;
        this.isDefault = isDefault;
        this.realCardNumber = realCardNumber;
    }
}
