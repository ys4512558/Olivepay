package kr.co.olivepay.donation.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.util.Date;

@Builder
public record DonationMyRes(
        Long franchiseId,
        String name,
        String address,
        Integer money,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd")
        Date date
) { }
