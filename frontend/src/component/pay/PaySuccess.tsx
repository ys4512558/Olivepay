import { Button, Success } from '../common';
import PayInfo from './PayInfo';
import { useNavigate } from 'react-router-dom';

const PaySuccess: React.FC<PaySuccessProps> = ({
  totalPrice,
  selectedCoupon,
  myCoupon,
}) => {
  const navigate = useNavigate();
  return (
    <>
      <section className="mb-8 mt-24 text-center">
        <Success />
        <h3 className="mt-16 text-2xl font-semibold">결제가 완료되었습니다.</h3>
      </section>
      <PayInfo
        totalPrice={totalPrice}
        couponPrice={
          selectedCoupon !== null
            ? +myCoupon.filter((el) => el.couponUserId === selectedCoupon)[0]
                .couponUnit
            : 0
        }
      />
      <section className="mx-8 mt-4">
        <Button label="확인" onClick={() => navigate('/history')} />
      </section>
    </>
  );
};

export default PaySuccess;
