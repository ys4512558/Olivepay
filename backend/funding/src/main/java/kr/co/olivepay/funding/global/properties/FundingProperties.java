package kr.co.olivepay.funding.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "config.fintech.funding")
public class FundingProperties {
	private String donationAccountNo;
	private String changeAccountNo;
	private String organizationAccountNo;
	private String userKey;
}
