package kr.co.olivepay.common.openapi.service;

import kr.co.olivepay.common.openapi.dto.res.ClovaRes;
import org.springframework.web.multipart.MultipartFile;

public interface ClovaService {

    ClovaRes getCardText(MultipartFile cardImg);

}
