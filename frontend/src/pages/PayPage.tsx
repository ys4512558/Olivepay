import { useState } from 'react';
import { useAtom } from 'jotai';
import { couponAtom } from '../atoms/userAtom';
import { Layout, PageTitle, BottomUp, Button } from '../component/common';
import { QrScan } from '../component/qr';
import { CheckPinCode, PayDetail, PaySuccess } from '../component/pay';
import { Helmet } from 'react-helmet';
import { franchise } from '../types/franchise';
import { getFranchiseDetail } from '../api/franchiseApi';
import { getMyCoupon } from '../api/couponApi';

const PayPage = () => {
  const [myCoupon, setMyCoupon] = useAtom(couponAtom);
  const [franchiseInfo, setFranchiseInfo] = useState<franchise | null>(null);
  const [steps, setSteps] = useState<number>(1);
  const [qrResult, setQrResult] = useState<string | null>(null);
  const [totalPrice, setTotalPrice] = useState<number>(0);
  const [selectedCoupon, setSelectedCoupon] = useState<number | null>(null);
  const [selectedCardId, setSelectedCardId] = useState<string | null>(null);

  const handleQrResult = async (result: string) => {
    const params = new URLSearchParams(result);
    const franchiseId = params.get('franchiseId');
    const amount = params.get('amount');
    if (franchiseId) {
      const store = await getFranchiseDetail(+franchiseId);
      setFranchiseInfo(store);
      const coupons = await getMyCoupon(+franchiseId);
      setMyCoupon(coupons);
    }
    if (amount) {
      setTotalPrice(+amount);
    }

    setQrResult(result);
  };

  const handleQrsteps = () => {
    setQrResult(null);
    setSteps(2);
  };

  const handlePaySteps = () => {
    setSteps(3);
  };

  const handlePaySuccess = (pinCode: string) => {
    console.log(
      selectedCardId,
      franchiseInfo?.franchiseId,
      totalPrice,
      pinCode,
      selectedCoupon,
    );
    // 여기에 websocket 관련 코드 작성하면 될듯
    // setSteps(4);
  };

  const handleCouponSelect = (couponIndex: number | null) => {
    setSelectedCoupon(couponIndex);
  };

  const handleCardSelect = (cardId: string) => {
    setSelectedCardId(cardId);
  };

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="QR을 통해 음식점과 금액을 인식하고 결제를 진행할 수 있습니다."
        />
      </Helmet>
      <Layout>
        <header className="mt-4 px-8 text-center">
          <PageTitle title="결제 " />
        </header>
        <main className="mb-16">
          {steps === 1 && <QrScan onResult={handleQrResult} />}
          {steps === 2 && (
            <PayDetail
              handlePaySteps={handlePaySteps}
              totalPrice={totalPrice}
              selectedCoupon={selectedCoupon}
              onCardSelect={handleCardSelect}
              onCouponSelect={handleCouponSelect}
              myCoupon={myCoupon}
            />
          )}
          {steps === 3 && (
            <CheckPinCode
              handlePaySuccess={(pinCode: string) => handlePaySuccess(pinCode)}
            />
          )}
          {steps === 4 && (
            <PaySuccess
              totalPrice={totalPrice}
              myCoupon={myCoupon}
              selectedCoupon={selectedCoupon}
            />
          )}
        </main>
      </Layout>
      {qrResult && (
        <BottomUp
          children={
            <div className="mb-20">
              <div className="mb-4 ml-4">
                <h2 className="font-semibold">
                  {franchiseInfo?.franchiseName}
                </h2>
                <div className="mt-2 text-base text-DARKBASE">
                  {franchiseInfo?.address}
                </div>
              </div>
              <Button label="입장하기" onClick={handleQrsteps} />
            </div>
          }
        />
      )}
    </>
  );
};

export default PayPage;
