import { useEffect, useState } from 'react';
import { useAtom } from 'jotai';
import { couponAtom } from '../atoms/userAtom';
import { Layout, PageTitle, BottomUp, Button } from '../component/common';
import { QrScan } from '../component/qr';
import { CheckPinCode, PayDetail, PaySuccess } from '../component/pay';

const PayPage = () => {
  const [myCoupon] = useAtom(couponAtom);
  const [steps, setSteps] = useState<number>(2);
  const [qrResult, setQrResult] = useState<string | null>(null);
  const [totalPrice, setTotalPrice] = useState<number>(15000);
  const [selectedCoupon, setSelectedCoupon] = useState<number | null>(null);
  const [selectedCardId, setSelectedCardId] = useState<string | null>(null);

  console.log(selectedCardId);
  useEffect(() => {
    setTotalPrice(10000);
  }, []);

  const handleQrResult = (result: string) => {
    setQrResult(result); // 가맹점 정보가 있을거임
  };

  const handleQrsteps = () => {
    setQrResult(null);
    setSteps(2);
  };

  const handlePaySteps = () => {
    setSteps(3);
  };

  const handlePaySuccess = () => {
    setSteps(4);
  };

  const handleCouponSelect = (couponIndex: number | null) => {
    setSelectedCoupon(couponIndex);
  };

  const handleCardSelect = (cardId: string) => {
    setSelectedCardId(cardId);
  };

  return (
    <>
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
          {steps === 3 && <CheckPinCode handlePaySuccess={handlePaySuccess} />}
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
            <div>
              <Button label="입장하기" onClick={handleQrsteps} />
            </div>
          }
        />
      )}
    </>
  );
};

export default PayPage;
