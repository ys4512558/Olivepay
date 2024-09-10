package kr.co.olivepay.card.global.enums;

import org.springframework.http.HttpStatus;

public interface ResponseCode {

    HttpStatus getHttpStatus();

    String getMessage();
}
