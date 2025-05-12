package kr.co.olivepay.transaction.config;

import kr.co.olivepay.transaction.state.account.AccountBalanceCheckFail;
import kr.co.olivepay.transaction.state.account.AccountBalanceCheckSuccess;
import kr.co.olivepay.transaction.state.coupon.*;
import kr.co.olivepay.transaction.state.payment.PaymentApplyFail;
import kr.co.olivepay.transaction.state.payment.PaymentApplyRollBackComplete;
import kr.co.olivepay.transaction.state.payment.PaymentApplySuccess;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 결제 상태 싱글톤 객체 등록
 */
@Configuration
public class PaymentStateConfig {

    @Bean
    public AccountBalanceCheckFail accountBalanceCheckFail() {
        return new AccountBalanceCheckFail();
    }
    @Bean
    public AccountBalanceCheckSuccess accountBalanceCheckSuccess() {
        return new AccountBalanceCheckSuccess();
    }
    @Bean
    public CouponTransferFail couponTransferFail() {
        return new CouponTransferFail();
    }
    @Bean
    public CouponTransferRollBackComplete couponTransferRollBackComplete() {
        return new CouponTransferRollBackComplete();
    }
    @Bean
    public CouponTransferSuccess couponTransferSuccess() {
        return new CouponTransferSuccess();
    }
    @Bean
    public CouponUsedFail couponUsedFail() {
        return new CouponUsedFail();
    }
    @Bean
    public CouponUsedSuccess couponUsedSuccess() {
        return new CouponUsedSuccess();
    }
    @Bean
    public PaymentApplyFail paymentApplyFail() {
        return new PaymentApplyFail();
    }
    @Bean
    public PaymentApplyRollBackComplete paymentApplyRollBackComplete() {
        return new PaymentApplyRollBackComplete();
    }
    @Bean
    public PaymentApplySuccess paymentApplySuccess() {
        return new PaymentApplySuccess();
    }
}
