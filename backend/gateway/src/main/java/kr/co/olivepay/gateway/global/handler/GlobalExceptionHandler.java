package kr.co.olivepay.gateway.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.olivepay.gateway.global.enums.ErrorCode;
import kr.co.olivepay.gateway.global.enums.NoneResponse;
import kr.co.olivepay.gateway.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static kr.co.olivepay.gateway.global.enums.ErrorCode.*;

@Slf4j
@Component
@Order(-1)// 기본적으로 사용하는 핸들러보다 우선순위를 높게
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // Json 타입으로 고정
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorCode errorCode;
        if (ex instanceof MethodArgumentNotValidException) {
            errorCode = BAD_REQUEST;    // 400
        } else if (ex instanceof HandlerMethodValidationException) {
            errorCode = BAD_REQUEST;    // 400
        } else if (ex instanceof NoResourceFoundException) {
            errorCode = NOT_FOUND; // 404
        } else if (ex instanceof ResponseStatusException) {
            errorCode = METHOD_NOT_ALLOWED; // 405
        } else if (ex instanceof AppException) {
            errorCode = ((AppException) ex).getErrorCode(); // custom
        } else {
            errorCode = INTERNAL_SERVER_ERROR;  // 500
        }

        return makeErrorResponse(errorCode, response);
    }


    private Mono<Void> makeErrorResponse(ErrorCode errorCode, ServerHttpResponse response) {
        response.setStatusCode(errorCode.getHttpStatus());

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();

            try {
                Response<Object> errorResponse = Response.error(errorCode, NoneResponse.NONE);
                byte[] errorResponseBytes = objectMapper.writeValueAsBytes(errorResponse);
                return bufferFactory.wrap(errorResponseBytes);
            } catch (Exception e) {
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
