import { useEffect } from 'react';
import { useAtom } from 'jotai';
import { useQuery } from '@tanstack/react-query';
import {
  Layout,
  BackButton,
  PageTitle,
  Coupon,
  EmptyData,
  Loader,
} from '../../component/common';
import { franchiseCouponAtom } from '../../atoms/franchiseAtom';
import { getMyStoreCoupon } from '../../api/couponApi';
import { getCurrentDate } from '../../utils/dateUtils';
import { Helmet } from 'react-helmet';

const CouponPage = () => {
  const [coupon, setCoupon] = useAtom(franchiseCouponAtom);

  const franchiseId = 2;

  const { data, error, isLoading, isSuccess } = useQuery({
    queryKey: ['coupon', franchiseId],
    queryFn: () => getMyStoreCoupon(franchiseId),
  });

  useEffect(() => {
    if (isSuccess && data) {
      setCoupon(data);
    }
  }, [isSuccess, data, setCoupon]);

  if (isLoading) return <Loader />;

  if (error) return <div>쿠폰 로딩 실패</div>;

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="가맹점주가 자신의 가맹점에 대해 발급된 쿠폰을 확인할 수 있습니다."
        />
      </Helmet>
      <Layout className="px-8">
        <header className="mt-4 flex items-center justify-between">
          <BackButton />
          <PageTitle title="보유 쿠폰 현황" />
          <div className="w-8" />
        </header>
        <main className="mt-8 flex flex-col items-center gap-6">
          <p className="text-md text-DARKBASE">
            {getCurrentDate()} 미사용 쿠폰
          </p>
          <hr />
          {coupon.coupon2 === 0 && coupon.coupon4 === 0 ? (
            <EmptyData label="미사용 쿠폰이 없습니다" />
          ) : (
            <>
              {coupon.coupon2 !== 0 && (
                <Coupon
                  storeName="멀티캠퍼스"
                  cost={2000}
                  forFranchiser={true}
                  count={coupon.coupon2}
                />
              )}
              {coupon.coupon4 !== 0 && (
                <Coupon
                  storeName="멀티캠퍼스"
                  cost={4000}
                  forFranchiser={true}
                  count={coupon.coupon4}
                />
              )}
            </>
          )}
        </main>
      </Layout>
    </>
  );
};

export default CouponPage;
