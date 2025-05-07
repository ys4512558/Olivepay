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
    public PaymentDLQOutBox saveDLQOutBox(DLQOutBoxReq dlqOutBoxReq) {
        PaymentDLQOutBox paymentDLQOutBox = PaymentDLQOutBox.builder()
                                                            .eventkey(dlqOutBoxReq.getKey())
                                                            .topic(dlqOutBoxReq.getTopic())
                                                            .payload(dlqOutBoxReq.getPayload())
                                                            .payloadType(dlqOutBoxReq.getPayloadType())
                                                            .errorMsg(dlqOutBoxReq.getErrorMsg())
                                                            .errorType(dlqOutBoxReq.getErrorType())
                                                            .build();
        return paymentDLQOutBoxRepository.save(paymentDLQOutBox);
    }

    @Override
    public void setSendDLQOutBox(Long id) {
        PaymentDLQOutBox paymentDLQOutBox = paymentDLQOutBoxRepository.findById(id)
                .orElseThrow(()-> {throw new RuntimeException();});

        paymentDLQOutBox.setIsSend(true);
        paymentDLQOutBoxRepository.save(paymentDLQOutBox);
    }
}

