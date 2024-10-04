package kr.co.olivepay.donation.repository;

import kr.co.olivepay.donation.entity.Donation;
import kr.co.olivepay.donation.entity.Donor;

import java.util.List;

public interface DonationRepositoryCustom{
    Long sumMoney();
    List<Donation> getMyDonation(final Donor donor, final Long index);
}
