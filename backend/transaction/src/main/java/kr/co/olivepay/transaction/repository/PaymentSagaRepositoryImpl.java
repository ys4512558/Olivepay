package kr.co.olivepay.transaction.repository;

import kr.co.olivepay.transaction.PaymentSaga;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PaymentSagaRepositoryImpl implements PaymentSagaRepository {

    private final ConcurrentHashMap<String, PaymentSaga> paymentSagaMap
        = new ConcurrentHashMap<>();

    @Override
    public void save(String id, PaymentSaga paymentSaga) {
        paymentSagaMap.put(id, paymentSaga);
    }

    @Override
    public PaymentSaga findById(String id) {
        return paymentSagaMap.get(id);
    }

    @Override
    public void deleteById(String id) {
        paymentSagaMap.remove(id);
    }
}
