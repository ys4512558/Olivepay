package kr.co.olivepay.payment.global.enums;

import org.springframework.http.HttpStatus;

public interface ResponseCode {

    HttpStatus getHttpStatus();

    String getMessage();
}
