import { useEffect, useMemo } from 'react';
import CardSelect from './CardSelect';

const PayInfo: React.FC<PaymentInfoProps> = ({
  totalPrice,
  couponPrice,
  onCardSelect,
}) => {
  const finalPayment = useMemo(() => {
    const payment = totalPrice - 9000 - couponPrice;
    return payment < 0 ? 0 : payment;
  }, [totalPrice, couponPrice]);

  const dreamCardPayment = useMemo(
    () => Math.min(9000, totalPrice),
    [totalPrice],
  );

  useEffect(() => {
    if (finalPayment === 0 && onCardSelect) {
      onCardSelect(null);
    }
  }, [finalPayment, onCardSelect]);

  return (
    <section className="mt-4">
      <h2 className="mx-8 mb-2 text-md">결제 금액</h2>
      <div className="mx-8 mb-8 flex flex-col gap-1 text-base">
        <p className="flex justify-between">
          상품 금액 <span>{totalPrice.toLocaleString()}원</span>
        </p>
        <p className="flex justify-between">
          꿈나무카드 자동결제 <span>{dreamCardPayment.toLocaleString()}원</span>
        </p>
        {couponPrice > 0 && totalPrice > 9000 && (
          <p className="flex justify-between">
            쿠폰 할인 금액 <span>{couponPrice.toLocaleString()}원</span>
          </p>
        )}
        <hr />
        <p className="flex justify-between font-semibold">
          최종 결제 금액 <span>{finalPayment.toLocaleString()}원</span>
        </p>
      </div>
      {finalPayment >= 0 && onCardSelect && (
        <CardSelect onCardSelect={onCardSelect} finalPayment={finalPayment} />
      )}
    </section>
  );
};

export default PayInfo;
