package kr.co.olivepay.donation.mapper;

import kr.co.olivepay.core.franchise.dto.res.FranchiseMyDonationRes;
import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.dto.res.DonationMyRes;
import kr.co.olivepay.donation.entity.Donation;
import kr.co.olivepay.donation.entity.Donor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DonationMapper {
    // 후원자와 후원req 를 통한 donation 객체 매핑
    @Mapping(source = "donationReq.money", target = "money")
    @Mapping(source = "donationReq.franchiseId", target = "franchiseId")
    Donation toEntity(Donor donor, DonationReq donationReq);

    // Donation과 FranchiseMyDonationRes를 통한 DonationMyRes로 매핑
    @Mapping(source = "franchiseMyDonationRes.franchiseId", target = "franchiseId")
    @Mapping(source = "franchiseMyDonationRes.name", target = "franchiseName")
    @Mapping(source = "franchiseMyDonationRes.address", target = "address")
    @Mapping(source = "donation.money", target = "money")
    @Mapping(source = "donation.createdAt", target = "date")
    DonationMyRes toDonationMyRes(Donation donation, FranchiseMyDonationRes franchiseMyDonationRes);
}
