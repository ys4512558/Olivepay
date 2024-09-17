import { useState } from 'react';

import { Layout, PageTitle, BottomUp, Button } from '../component/common';
import { QrScan } from '../component/qr';
import { CheckPinCode } from '../component/pay';

const PayPage = () => {
  const [steps, Setsteps] = useState<number>(2);
  const [qrResult, setQrResult] = useState<string | null>(null);

  const handleQrResult = (result: string) => {
    setQrResult(result); // 가맹점 정보가 있을거임
  };

  const handleQrsteps = () => {
    setQrResult(null);
    Setsteps(2);
  };

  return (
    <>
      <Layout className="px-8">
        <header className="mt-8 text-center">
          <PageTitle title="결제 " />
        </header>
        <main>
          {steps === 1 && <QrScan onResult={handleQrResult} />}
          {steps === 2 && <CheckPinCode />}
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
