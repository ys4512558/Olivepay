import { useAtom } from 'jotai';
import { useQuery } from '@tanstack/react-query';
import { couponAtom } from '../../atoms/userAtom';
import { Coupon, EmptyData, Loader } from '../common';
import { getMyCoupon } from '../../api/couponApi';

const MyCoupon = () => {
  const [coupons, setCoupons] = useAtom(couponAtom);

  const { data, error, isLoading, isSuccess } = useQuery({
    queryKey: ['user-coupon'],
    queryFn: () => getMyCoupon(),
  });

  if (isSuccess && data) {
    setCoupons(data);
  }

  if (isLoading) return <Loader />;

  if (error) return <div>쿠폰 로딩 실패</div>;
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
