package kr.co.olivepay.donation.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.olivepay.donation.entity.Donation;
import kr.co.olivepay.donation.entity.Donor;
import kr.co.olivepay.donation.repository.DonationRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.co.olivepay.donation.entity.QDonation.donation;


@RequiredArgsConstructor
public class DonationRepositoryCustomImpl implements DonationRepositoryCustom {

    private final long PAGE_SIZE = 20;
    private final JPAQueryFactory queryFactory;

    @Override
    public Long sumMoney() {
        Long sum =  queryFactory.select(donation.money.sum())
                           .from(donation)
                           .fetchOne();
        return sum == null ? 0L : sum;
    }

    @Override
    public List<Donation> getMyDonation(Donor donor, Long index) {
        return queryFactory
                .selectFrom(donation)
                .where(
                        donation.donor.eq(donor)
                                      .and(donation.id.gt(index))
                )
                .orderBy(donation.createdAt.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }


}
