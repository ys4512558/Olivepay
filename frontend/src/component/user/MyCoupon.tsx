import { useAtom } from 'jotai';
import { couponAtom } from '../../atoms/userAtom';
import { Coupon, EmptyData } from '../common';

const MyCoupon = () => {
  const [coupons] = useAtom(couponAtom);
  return (
    <div className="mt-4 flex flex-col items-center gap-2">
      <h1 className="w-full border-b-2 pb-4 text-center text-lg font-semibold">
        보유 쿠폰 현황
      </h1>
      {coupons.length === 0 && <EmptyData label="보유 쿠폰이 없습니다." />}
      {coupons.map((coupon) => (
        <div key={coupon.franchiseId + coupon.couponUnit}>
          <Coupon storeName={coupon.franchiseName} cost={+coupon.couponUnit} />
        </div>
      ))}
    </div>
  );
};

export default MyCoupon;
