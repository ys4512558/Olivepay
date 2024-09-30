package kr.co.olivepay.franchise.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "member")
public interface MemberServiceClient {
}
