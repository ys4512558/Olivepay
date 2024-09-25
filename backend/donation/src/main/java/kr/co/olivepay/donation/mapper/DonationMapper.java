package kr.co.olivepay.donation.mapper;

import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.entity.Donation;
import kr.co.olivepay.donation.entity.Donor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DonationMapper {
    @Mapping(source = "donationReq.money", target = "money")
    @Mapping(source = "donationReq.franchiseId", target = "franchiseId")
    Donation toEntity(Donor donor, DonationReq donationReq);
}
