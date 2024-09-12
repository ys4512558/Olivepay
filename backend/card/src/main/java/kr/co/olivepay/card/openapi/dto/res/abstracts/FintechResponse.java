package kr.co.olivepay.card.openapi.dto.res.abstracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.co.olivepay.card.openapi.dto.res.FintechHeaderRes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 실직적으로 API 요청에 대한 반환 형식
 * Header:{},REC:{}
 * REC은 요청에 따라 객체 필드가 달라지므로
 * 상속받은 객체에서 이를 추가
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class FintechResponse {

    @JsonProperty("Header")
    private FintechHeaderRes Header;

}
