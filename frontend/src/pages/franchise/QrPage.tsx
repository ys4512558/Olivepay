import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import { franchiseAtom } from '../../atoms';
import {
  BackButton,
  Button,
  Layout,
  PageTitle,
  BottomUp,
} from '../../component/common';
import { QrInput, QrView } from '../../component/qr';
import { makeQr } from '../../api/franchiseApi';

const QrPage = () => {
  const navigate = useNavigate();
  const [franchiseInfo, setFranchiseInfo] = useAtom(franchiseAtom);
  const [steps, setSteps] = useState<number>(1);
  const [input, setInput] = useState<string>('');
  const [img, setImg] = useState<string>('');

  const handleQr = async () => {
    if (steps === 1) {
      // const result = await makeQr(franchiseInfo.franchiseId, input);
      // setImg(result.image);
      setImg('123');
      setSteps(2);
    } else {
      navigate('/franchise/home');
    }
  };
  const backStep = () => {
    setImg('');
    setInput('');
    setSteps(1);
  };
  return (
    <>
      <Layout className="px-8">
        <header className="mt-12 flex items-center justify-between">
          <BackButton onClick={steps === 2 ? backStep : undefined} />
          <PageTitle title={steps === 1 ? 'QR 코드 생성' : 'QR 코드'} />
          <div className="w-8" />
        </header>
        <main className="flex flex-col items-center gap-4">
          <h3 className="mt-12 text-center text-DARKBASE">
            {steps === 1
              ? '결제해야 하는 총 금액을 입력해주세요'
              : '아래 QR 코드를 스캔해주세요'}
          </h3>

          <section className="mt-8 w-full">
            {steps === 1 && (
              <QrInput
                value={input}
                onChange={(e) => setInput(e.target.value)}
              />
            )}
          </section>
          <Button
            className="mt-4"
            label={steps === 1 ? 'QR 생성하기' : '확인'}
            onClick={handleQr}
          />
        </main>
      </Layout>
      {img && <BottomUp children={<QrView img={img} />} />}
    </>
  );
};

export default QrPage;
