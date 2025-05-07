package kr.co.olivepay.core.outbox.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@NoArgsConstructor
public class DLQOutBoxReq extends OutBoxReq {
    private String errorMsg;
    private String errorType;
}
