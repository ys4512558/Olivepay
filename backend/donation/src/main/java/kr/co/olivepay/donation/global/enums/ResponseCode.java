package kr.co.olivepay.donation.global.enums;

import org.springframework.http.HttpStatus;

public interface ResponseCode {

    HttpStatus getHttpStatus();

    String getMessage();
}
