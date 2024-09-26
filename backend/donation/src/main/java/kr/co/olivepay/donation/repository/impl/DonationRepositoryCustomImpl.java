package kr.co.olivepay.donation.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.olivepay.donation.entity.QDonation;
import kr.co.olivepay.donation.repository.DonationRepositoryCustom;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DonationRepositoryCustomImpl implements DonationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long sumMoney() {
        QDonation donation = QDonation.donation;
        Long sum =  queryFactory.select(donation.money.sum())
                           .from(donation)
                           .fetchOne();
        return sum == null ? 0L : sum;
    }


}
