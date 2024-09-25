package kr.co.olivepay.franchise.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.springframework.stereotype.Service;

import kr.co.olivepay.franchise.dto.res.QrCodeRes;
import kr.co.olivepay.franchise.global.enums.ErrorCode;
import kr.co.olivepay.franchise.global.enums.SuccessCode;
import kr.co.olivepay.franchise.global.handler.AppException;
import kr.co.olivepay.franchise.global.response.SuccessResponse;
import kr.co.olivepay.franchise.mapper.FranchiseMapper;
import kr.co.olivepay.franchise.service.QrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrServiceImpl implements QrService {

	private final FranchiseMapper franchiseMapper;

	private static final Integer QR_CODE_SIZE=300;
	private static final String QR_CODE_FORMAT="PNG";
	private static final String QR_CODE_DATA_FORMAT = "franchiseId=%d&amount=%d";

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
			BitMatrix bitMatrix = qrCodeWriter.encode(String.format(QR_CODE_DATA_FORMAT, franchiseId, amount), BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE);
			MatrixToImageWriter.writeToStream(bitMatrix, QR_CODE_FORMAT, pngOutputStream);
			QrCodeRes response = franchiseMapper.toQrCodeRes(Base64.getEncoder().encodeToString(pngOutputStream.toByteArray()));
			return new SuccessResponse<>(SuccessCode.QR_CREATE_SUCCESS, response);
		} catch (Exception e) {
			throw new AppException(ErrorCode.QR_CREATE_ERROR);
		}
	}
}
