package kr.co.olivepay.member.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import kr.co.olivepay.member.dto.req.MemberRegisterReq;
import kr.co.olivepay.member.dto.req.UserRegisterReq;

import java.io.IOException;

public class UserRegisterReqDeserializer extends JsonDeserializer<UserRegisterReq> {

    @Override
    public UserRegisterReq deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        MemberRegisterReq memberRegisterReq = MemberRegisterReq.builder()
                                                               .name(node.get("name").asText())
                                                               .password(node.get("password").asText())
                                                               .phoneNumber(node.get("phoneNumber").asText())
                                                               .build();

        String nickname = node.get("nickname").asText();
        String birthdate = node.get("birthdate").asText();
        String pin = node.get("pin").asText();

        return UserRegisterReq.builder()
                              .memberRegisterReq(memberRegisterReq)
                              .nickname(nickname)
                              .birthdate(birthdate)
                              .pin(pin)
                              .build();
    }
}
