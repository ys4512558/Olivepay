package kr.co.olivepay.donation.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.olivepay.donation.repository.DonationRepositoryCustom;
import lombok.RequiredArgsConstructor;

import static kr.co.olivepay.donation.entity.QDonation.donation;


@RequiredArgsConstructor
public class DonationRepositoryCustomImpl implements DonationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long sumMoney() {
        Long sum =  queryFactory.select(donation.money.sum())
                           .from(donation)
                           .fetchOne();
        return sum == null ? 0L : sum;
    }


}
