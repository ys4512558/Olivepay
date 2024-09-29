package kr.co.olivepay.member.dto.res;

import lombok.Builder;

@Builder
public record UserPasswordCheckRes (Boolean isCorrect){ }
