package kr.co.olivepay.payment.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="funding")
public interface FundingServiceClient {
}
