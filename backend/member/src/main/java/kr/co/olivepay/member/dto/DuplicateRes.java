package kr.co.olivepay.member.dto;

import lombok.Builder;

@Builder
public record DuplicateRes(Boolean isDuplicate) { }
