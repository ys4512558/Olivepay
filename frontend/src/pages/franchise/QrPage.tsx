import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// import { useAtom } from 'jotai';
// import { franchiseAtom } from '../../atoms';
import {
  BackButton,
  Button,
  Layout,
  PageTitle,
  BottomUp,
} from '../../component/common';
import { QrInput, QrView } from '../../component/qr';
// import { makeQr } from '../../api/franchiseApi';
import { numericRegex } from '../../utils/validators';

const QrPage = () => {
  const navigate = useNavigate();
  // const [franchiseInfo] = useAtom(franchiseAtom);
  const [steps, setSteps] = useState<number>(1);
  const [input, setInput] = useState<string>('');
  const [img, setImg] = useState<string>('');

  const handleQr = async () => {
    if (steps === 1) {
      // const result = await makeQr(franchiseInfo.franchiseId, input);
      // setImg(result.image);
      setImg('asdas');
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
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    if (numericRegex.test(value)) {
      setInput(value);
    }
  };
  return (
    <>
      <Layout className="px-8">
        <header className="mt-4 flex items-center justify-between">
          <BackButton onClick={steps === 2 ? backStep : undefined} />
          <PageTitle title={steps === 1 ? 'QR 코드 생성' : 'QR 코드'} />
          <div className="w-8" />
        </header>
        <main className="flex flex-col items-center gap-4">
          <h3 className="mt-12 text-center text-base text-DARKBASE">
            {steps === 1
              ? '결제해야 하는 총 금액을 입력해주세요'
              : '아래 QR 코드를 스캔해주세요'}
          </h3>

          <section className="mt-8 w-full">
            {steps === 1 && (
              <QrInput value={input} onChange={handleInputChange} />
            )}
          </section>
          <section>
            <Button
              className="w-40 text-base font-semibold"
              label={steps === 1 ? 'QR 생성하기' : '확인'}
              onClick={handleQr}
            />
          </section>
        </main>
      </Layout>
      {img && <BottomUp children={<QrView img={img} />} />}
    </>
  );
};

export default QrPage;
