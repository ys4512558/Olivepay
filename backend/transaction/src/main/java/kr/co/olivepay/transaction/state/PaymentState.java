package kr.co.olivepay.transaction.state;


import kr.co.olivepay.transaction.PaymentSaga;

public interface PaymentState {

    void operate(PaymentSaga paymentSaga);
}
