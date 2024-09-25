package kr.co.olivepay.common.global.enums;

import org.springframework.http.HttpStatus;

public interface ResponseCode {

    HttpStatus getHttpStatus();

    String getMessage();
}
