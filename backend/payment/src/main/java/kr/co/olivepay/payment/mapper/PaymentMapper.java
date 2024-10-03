package kr.co.olivepay.payment.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import kr.co.olivepay.payment.dto.req.PaymentCreateReq;
import kr.co.olivepay.payment.dto.res.PaymentHistoryFranchiseRes;
import kr.co.olivepay.payment.dto.res.PaymentHistoryRes;
import kr.co.olivepay.payment.entity.Payment;
import kr.co.olivepay.payment.entity.PaymentDetail;
import kr.co.olivepay.payment.entity.enums.PaymentState;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
	@Mapping(source = "payment.id", target = "paymentId")
	@Mapping(source = "paymentDetailList", target = "amount", qualifiedByName = "calculateTotalAmount")
	@Mapping(source = "paymentDetailList", target = "details")
	PaymentHistoryRes toPaymentHistoryRes(Payment payment, List<PaymentDetail> paymentDetailList);

	@Mapping(source = "payment.id", target = "paymentId")
	@Mapping(source = "paymentDetailList", target = "amount", qualifiedByName = "calculateTotalAmount")
	@Mapping(source = "paymentDetailList", target = "details")
	PaymentHistoryFranchiseRes toPaymentHistoryFranchiseRes(Payment payment, String franchiseName, List<PaymentDetail> paymentDetailList);
	@Named("calculateTotalAmount")
	default Long calculateTotalAmount(List<PaymentDetail> paymentDetails) {
		if (paymentDetails == null || paymentDetails.isEmpty()) {
			return 0L;
		}
		return paymentDetails.stream()
							 .mapToLong(PaymentDetail::getAmount)
							 .sum();
	}

}
