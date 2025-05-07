package kr.co.olivepay.payment.service;

import kr.co.olivepay.core.outbox.dto.req.DLQOutBoxReq;
import kr.co.olivepay.payment.entity.PaymentDLQOutBox;

public interface PaymentDLQOutBoxService {
    PaymentDLQOutBox saveDLQOutBox(DLQOutBoxReq dlqOutBoxReq);

    void setSendDLQOutBox(Long id);
}
