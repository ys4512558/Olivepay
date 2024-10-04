package kr.co.olivepay.payment.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "member")
public interface MemberServiceClient {
}
