package kr.co.olivepay.member.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import kr.co.olivepay.core.franchise.dto.req.FranchiseCreateReq;
import kr.co.olivepay.member.dto.req.MemberRegisterReq;
import kr.co.olivepay.member.dto.req.OwnerRegisterReq;

import java.io.IOException;

public class OwnerRegisterReqDeserializer extends JsonDeserializer<OwnerRegisterReq> {

    @Override
    public OwnerRegisterReq deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        MemberRegisterReq memberRegisterReq = MemberRegisterReq.builder()
                                                               .name(node.get("name").asText())
                                                               .password(node.get("password").asText())
                                                               .phoneNumber(node.get("phoneNumber").asText())
                                                               .build();

        FranchiseCreateReq franchiseCreateReq = FranchiseCreateReq.builder()
                                                                  .registrationNumber(node.get("registrationNumber").asText())
                                                                  .name(node.get("name").asText()) // 중복된 이름 필드
                                                                  .category(node.get("category").asText())
                                                                  .telephoneNumber(node.get("telephoneNumber").asText())
                                                                  .address(node.get("address").asText())
                                                                  .latitude(node.get("latitude").asDouble())
                                                                  .longitude(node.get("longitude").asDouble())
                                                                  .build();

        String rrnPrefix = node.get("rrnPrefix").asText();
        Integer rrnCheckDigit = node.get("rrnCheckDigit").asInt();

        return OwnerRegisterReq.builder()
                               .memberRegisterReq(memberRegisterReq)
                               .franchiseCreateReq(franchiseCreateReq)
                               .rrnPrefix(rrnPrefix)
                               .rrnCheckDigit(rrnCheckDigit)
                               .build();
    }
}