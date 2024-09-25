import { Button } from '../common';
import clsx from 'clsx';
import { CheckIcon } from '@heroicons/react/24/outline';

import PayInfo from './PayInfo';

interface PayDetailProps {
  handlePaySteps: () => void;
  onCardSelect: (cardId: string) => void;
  totalPrice: number;
  selectedCoupon: number | null;
  onCouponSelect: (couponIndex: number | null) => void;
  myCoupon: myCoupon[];
}

const PayDetail: React.FC<PayDetailProps> = ({
  handlePaySteps,
  onCardSelect,
  totalPrice,
  selectedCoupon,
  onCouponSelect,
  myCoupon,
}) => {
  const handleCheckboxChange = (index: number) => {
    if (selectedCoupon === index) {
      onCouponSelect(null);
    } else {
      onCouponSelect(index);
    }
  };

  return (
    <>
      {myCoupon.length > 0 && (
        <section className="mx-8 mt-4">
          <h3 className="text-md mb-4 ml-2 text-DARKBASE">
            사용 가능한 쿠폰 목록
          </h3>
          <div className="rounded-md border-2">
            {myCoupon.map((coupon, index) => (
              <div
                key={index}
                className={clsx(
                  index !== myCoupon.length - 1 ? 'border-b' : '',
                )}
              >
                <label className="text-md relative flex items-center gap-4 p-4">
                  <input
                    type="checkbox"
                    className="size-5 appearance-none rounded-md border border-DARKBASE checked:border-PRIMARY checked:bg-PRIMARY"
                    checked={selectedCoupon === index}
                    onChange={() => handleCheckboxChange(index)}
                  />
                  <span
                    className={clsx(
                      'absolute left-[18px] top-[19px] flex items-center justify-center',
                      selectedCoupon === index ? 'block' : 'hidden',
                    )}
                  >
                    <CheckIcon className="size-4 text-white" strokeWidth={3} />
                  </span>
                  {coupon.franchiseName} - {coupon.couponUnit}원 할인
                </label>
              </div>
            ))}
          </div>
        </section>
      )}
      <PayInfo
        totalPrice={totalPrice}
        couponPrice={
          selectedCoupon !== null ? +myCoupon[selectedCoupon].couponUnit : 0
        }
        onCardSelect={onCardSelect}
      />
      <div className="mx-4">
        <Button
          label="결제하기"
          onClick={handlePaySteps}
          className="mb-4 mt-2"
        />
      </div>
    </>
  );
};

export default PayDetail;
