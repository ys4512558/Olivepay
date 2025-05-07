package kr.co.olivepay.payment.service;

import kr.co.olivepay.core.outbox.dto.req.DLQOutBoxReq;

public interface PaymentDLQOutBoxService {
    void saveDLQOutBox(DLQOutBoxReq dlqOutBoxReq);

}
