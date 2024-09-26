package kr.co.olivepay.transaction.repository;


import kr.co.olivepay.transaction.PaymentSaga;

public interface PaymentSagaRepository {

    //PaymentSaga 저장
    void save(String id, PaymentSaga paymentSaga);
    //Id로 PaymentSage 객체 찾기
    PaymentSaga findById(String id);
    void deleteById(String id);

}
