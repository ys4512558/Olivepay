package kr.co.olivepay.donation.dto.res;

public record DonationMyRes(
        Long franchiseId,
        String name,
        String address,
        Integer money,
        String date
) { }
