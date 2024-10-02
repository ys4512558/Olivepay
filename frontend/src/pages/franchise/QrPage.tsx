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
import { numericRegex } from '../../utils/validators';
import { useSnackbar } from 'notistack';
import { Helmet } from 'react-helmet';

const QrPage = () => {
  const { enqueueSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const [franchiseInfo] = useAtom(franchiseAtom);
  const [steps, setSteps] = useState<number>(1);
  const [input, setInput] = useState<string>('');
  const [img, setImg] = useState<string>('');

  const handleQr = async () => {
    if (steps === 1) {
      try {
        const result = await makeQr(franchiseInfo.franchiseId, input);
        setImg(result.image);
        setSteps(2);
      } catch (error) {
        if (error instanceof Error) {
          if (error.message.includes('400')) {
            enqueueSnackbar('금액을 입력해주세요', { variant: 'warning' });
          } else {
            enqueueSnackbar('오류가 발생했습니다.', { variant: 'error' });
          }
        } else {
          enqueueSnackbar('알 수 없는 오류가 발생했습니다.', {
            variant: 'error',
          });
        }
      }
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
      <Helmet>
        <meta
          name="description"
          content="가맹점주가 금액을 입력하여 QR을 생성할 수 있습니다."
        />
      </Helmet>
      <Layout className="px-8">
        <header className="mt-4 flex items-center justify-between">
          <BackButton onClick={steps === 2 ? backStep : undefined} />
          <PageTitle title={steps === 1 ? 'QR 코드 생성' : 'QR 코드'} />
          <div className="w-8" />
        </header>
        <main className="flex flex-col items-center gap-4">
          <h3 className="mt-8 text-center text-base text-DARKBASE">
            {steps === 1
              ? '결제해야 하는 총 금액을 입력해주세요'
              : '아래 QR 코드를 스캔해주세요'}
          </h3>

          <section className="mt-4 w-full">
            {steps === 1 && (
              <QrInput value={input} onChange={handleInputChange} />
            )}
          </section>
          <section className="w-1/2">
            <Button
              className="text-base font-semibold"
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
