package kr.co.olivepay.payment.service;

import kr.co.olivepay.core.outbox.dto.req.DLQOutBoxReq;
import kr.co.olivepay.payment.entity.PaymentDLQOutBox;
import kr.co.olivepay.payment.repository.PaymentDLQOutBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentDLQOutBoxServiceImpl implements PaymentDLQOutBoxService {

    private final PaymentDLQOutBoxRepository paymentDLQOutBoxRepository;

    @Override
    public void saveDLQOutBox(DLQOutBoxReq dlqOutBoxReq) {
        PaymentDLQOutBox paymentDLQOutBox = PaymentDLQOutBox.builder()
                                                            .eventkey(dlqOutBoxReq.getKey())
                                                            .topic(dlqOutBoxReq.getTopic())
                                                            .payload(dlqOutBoxReq.getPayload())
                                                            .payloadType(dlqOutBoxReq.getPayloadType())
                                                            .errorMsg(dlqOutBoxReq.getErrorMsg())
                                                            .errorType(dlqOutBoxReq.getErrorType())
                                                            .build();
        paymentDLQOutBoxRepository.save(paymentDLQOutBox);
    }
}

