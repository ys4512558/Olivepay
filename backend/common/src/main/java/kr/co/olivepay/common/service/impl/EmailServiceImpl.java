package kr.co.olivepay.common.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.co.olivepay.core.common.dto.req.EmailReq;
import kr.co.olivepay.common.global.enums.ErrorCode;
import kr.co.olivepay.common.global.enums.NoneResponse;
import kr.co.olivepay.common.global.handler.AppException;
import kr.co.olivepay.common.global.response.SuccessResponse;
import kr.co.olivepay.common.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.concurrent.CompletableFuture;

import static kr.co.olivepay.common.global.enums.NoneResponse.NONE;
import static kr.co.olivepay.common.global.enums.SuccessCode.EMAIL_SEND_SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final String ENCODING = "UTF-8";
    private final String SUBJECT = "올리브페이 후원 사용 내역 ☘";
    private final TemplateEngine templateEngine;

    @Async
    @Override
    public CompletableFuture<SuccessResponse<NoneResponse>> send(EmailReq request) {
        String html = makeHtml(request);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, ENCODING);
            mimeMessageHelper.setTo(request.email());
            mimeMessageHelper.setSubject(SUBJECT);
            mimeMessageHelper.setText(html, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.BAD_REQUEST);
        }
        return CompletableFuture.completedFuture(new SuccessResponse<>(EMAIL_SEND_SUCCESS, NONE));
    }

    /**
     * 타임리프를 이용해 HTML을 동적으로 생성하는 메서드
     * @param request 이메일, 후원 내역이 담긴 DTO {@link EmailReq}
     * @return 생성된 HTML
     */
    private String makeHtml(EmailReq request) {
        Context context = new Context();
        context.setVariable("emailAddress", request.email());
        context.setVariable("usageList", request.histories());
        return templateEngine.process("email-template", context);
    }

}
