package kr.co.olivepay.card.entity;

import jakarta.persistence.*;
import kr.co.olivepay.card.enums.AccountType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @Column(name = "account_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 16)
    private String accountNumber;

    @Column(nullable = false, length = 3)
    private String bankCode;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;


}
