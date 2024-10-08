package kr.co.olivepay.common.service.impl;

import jakarta.annotation.PostConstruct;
import kr.co.olivepay.common.dto.req.SMSCheckReq;
import kr.co.olivepay.common.dto.req.SMSReq;
import kr.co.olivepay.common.entity.SMS;
import kr.co.olivepay.common.global.enums.NoneResponse;
import kr.co.olivepay.common.global.handler.AppException;
import kr.co.olivepay.common.global.response.SuccessResponse;
import kr.co.olivepay.common.repository.SMSRepository;
import kr.co.olivepay.common.service.SMSService;
import kr.co.olivepay.common.util.RandomCodeGenerator;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static kr.co.olivepay.common.global.enums.ErrorCode.SMS_CODE_INVALID;
import static kr.co.olivepay.common.global.enums.NoneResponse.NONE;
import static kr.co.olivepay.common.global.enums.SuccessCode.SMS_CHECK_SUCCESS;
import static kr.co.olivepay.common.global.enums.SuccessCode.SMS_SEND_SUCCESS;

@Service
@RequiredArgsConstructor
public class SMSServiceImpl implements SMSService {
    @Value("${spring.sms.api-key}")
    private String apiKey;
    @Value("${spring.sms.api-secret}")
    private String apiSecret;
    @Value("${spring.sms.provider}")
    private String smsProvider;
    @Value("${spring.sms.sender}")
    private String smsSender;

    private final Long MESSAGE_VALID_TIME = 3 * 60L;
    private final String MESSAGE_PREFIX = "[OLIVEPAY] 인증번호는 ";
    private final String MESSAGE_SUFFIX = " 입니다.";
    private DefaultMessageService messageService;
    private final SMSRepository smsRepository;

    @PostConstruct
    public void init() {
        messageService = NurigoApp.INSTANCE.initialize(
                apiKey, apiSecret, smsProvider
        );
    }

    @Override
    public SuccessResponse<NoneResponse> sendSMS(SMSReq request) {
        Message message = new Message();
        message.setFrom(smsSender);
        message.setTo(request.phone());
        String code = RandomCodeGenerator.generateSixDigitCode();
        message.setText(makeMessage(code));
        SMS sms = SMS.builder()
                     .phone(request.phone())
                     .code(code)
                     .ttl(MESSAGE_VALID_TIME)
                     .build();
        messageService.sendOne(new SingleMessageSendingRequest(message));
        smsRepository.save(sms);
        return new SuccessResponse<>(SMS_SEND_SUCCESS, NONE);
    }

    @Override
    public SuccessResponse<NoneResponse> verifyCode(SMSCheckReq request) {
        SMS sms = smsRepository.findByPhone(request.phone())
                     .orElseThrow(() -> new AppException(SMS_CODE_INVALID));
        if(!sms.getCode().equals(request.code()))
            throw new AppException(SMS_CODE_INVALID);
        smsRepository.delete(sms);
        return new SuccessResponse<>(SMS_CHECK_SUCCESS, NONE);
    }

    private String makeMessage(String code) {
        StringBuilder message = new StringBuilder();
        message.append(MESSAGE_PREFIX)
               .append(code)
               .append(MESSAGE_SUFFIX);
        return message.toString();
    }

}
