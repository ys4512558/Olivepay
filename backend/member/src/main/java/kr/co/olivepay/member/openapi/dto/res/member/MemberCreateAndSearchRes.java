package kr.co.olivepay.member.openapi.dto.res.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MemberCreateAndSearchRes{

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("username")
    private String username;
    @JsonProperty("institutionCode")
    private String institutionCode;
    @JsonProperty("userKey")
    private String userKey;
    @JsonProperty("created")
    private String created;
    @JsonProperty("modified")
    private String modified;

}
