package kr.co.olivepay.payment.controller;

import kr.co.olivepay.core.transaction.util.KeyGenerator;
import kr.co.olivepay.payment.global.enums.SuccessCode;
import kr.co.olivepay.payment.global.response.Response;
import kr.co.olivepay.payment.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/payments")
public class SocketTestController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    @Value("${config.socket.TOPIC_PREFIX}")
    private String TOPIC_PREFIX;

    @GetMapping("/key")
    public ResponseEntity<Response<String>> getKey() {
        SuccessResponse<String> response
                = new SuccessResponse<>(SuccessCode.SUCCESS, KeyGenerator.makeKey());
        return Response.success(response);
    }

    @GetMapping("/send/{key}")
    public ResponseEntity<Response<String>> sendMessage(@PathVariable("key") String key) {

        String topic = TOPIC_PREFIX + "/" + key;
        simpMessagingTemplate.convertAndSend(topic, "메시지 전송 완료");
        SuccessResponse<String> response
                = new SuccessResponse<>(SuccessCode.SUCCESS, "메시지 전송 완료");
        return Response.success(response);
    }
}
