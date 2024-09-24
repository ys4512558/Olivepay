package kr.co.olivepay.franchise.service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.springframework.stereotype.Service;

import kr.co.olivepay.franchise.dto.res.QrCodeRes;
import kr.co.olivepay.franchise.global.enums.ErrorCode;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.handler.AppException;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.mapper.FranchiseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrServiceImpl implements QrService{

	private final FranchiseMapper franchiseMapper;

	/**
	 * 결제 QR 코드 생성
	 * @param franchiseId
	 * @param amount
	 * @return
	 */
	@Override
	public SuccessResponse<QrCodeRes> getQrCode(Long franchiseId, Integer amount) {
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
			BitMatrix bitMatrix = qrCodeWriter.encode(String.format("franchiseId=%d&amount=%d", franchiseId, amount), BarcodeFormat.QR_CODE, 300, 300);
			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
			QrCodeRes response = franchiseMapper.toQrCodeRes(Base64.getEncoder().encodeToString(pngOutputStream.toByteArray()));
			return new SuccessResponse<>(SuccessCode.QR_CREATE_SUCCESS, response);
		} catch (Exception e) {
			throw new AppException(ErrorCode.QR_CREATE_ERROR);
		}
	}
}
