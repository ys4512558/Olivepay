package kr.co.olivepay.donation.client;

import kr.co.olivepay.core.common.dto.req.EmailReq;
import kr.co.olivepay.donation.global.enums.NoneResponse;
import kr.co.olivepay.donation.global.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "common")
public interface CommonServiceClient {
    @PostMapping("/api/commons/email/send")
    Response<NoneResponse> sendEmail(@RequestBody EmailReq request);
}
