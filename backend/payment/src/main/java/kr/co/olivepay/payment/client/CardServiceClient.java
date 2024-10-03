package kr.co.olivepay.payment.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="card")
public interface CardServiceClient {
}
