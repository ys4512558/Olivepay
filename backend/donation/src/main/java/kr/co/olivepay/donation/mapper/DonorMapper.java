package kr.co.olivepay.donation.mapper;

import kr.co.olivepay.donation.dto.req.DonationReq;
import kr.co.olivepay.donation.entity.Donor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonorMapper {
    Donor toEntity(DonationReq donationReq);
}
