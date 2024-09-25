package kr.co.olivepay.transaction.state;

import kr.co.olivepay.transaction.PaymentSaga;

/**
 * 결제 요청 프로세스 실패
 */
public class PaymentFail implements PaymentState {

    @Override
    public void operate(PaymentSaga paymentSaga) {

    }
}
