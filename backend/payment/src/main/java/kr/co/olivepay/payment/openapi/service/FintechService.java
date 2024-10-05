package kr.co.olivepay.payment.openapi.service;

import kr.co.olivepay.payment.openapi.dto.res.card.rec.CreateCardTransactionRec;
import kr.co.olivepay.payment.openapi.dto.res.card.rec.DeleteCardTransactionRec;

public interface FintechService {

    CreateCardTransactionRec processCardPayment(String userKey, String cardNo, String cvc, Long merchantId, Long payementBalance);
    DeleteCardTransactionRec cancelCardPayment(String userKey, String cardNo, String cvc, Long trasactionUniqueNo);

}
