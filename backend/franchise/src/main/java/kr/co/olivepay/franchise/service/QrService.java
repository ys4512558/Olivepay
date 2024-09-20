package kr.co.olivepay.franchise.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.olivepay.franchise.dto.res.QrCodeRes;
import kr.co.olivepay.franchise.global.response.Response;
import kr.co.olivepay.franchise.global.response.SuccessResponse;

@Service
public interface QrService {

	/**
	 * 결제 QR 코드 생성
	 * @param franchiseId
	 * @param amount
	 * @return
	 */
	public SuccessResponse<QrCodeRes> getQrCode(Long franchiseId, Integer amount);

}
