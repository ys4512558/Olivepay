import { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { Layout, PageTitle, BackButton } from '../component/common';
import {
  Donate1,
  Donate2,
  Donate3,
  Donate4,
  Donate5,
} from '../component/donate';
import { toDonate } from '../api/donationApi';

const DonatePage = () => {
  const location = useLocation();
  const { franchiseId } = location.state || {};

  const [step, setStep] = useState(1);

  const [donateInfo, setDonateInfo] = useState({
    email: '',
    phoneNumber: '',
    coupon2: 0,
    coupon4: 0,
    money: 0,
    message: '',
    accountNumber: '',
  });

  const handleNextStep = () => {
    if (step === 2) {
      if (donateInfo.money <= 0) {
        alert('금액을 입력해주세요.');
        return;
      }
      if (!donateInfo.message.trim()) {
        alert('쿠폰 멘트를 입력해주세요.');
        return;
      }
    }
    if (step === 4) {
      handleSubmit();
    }
    setStep(step + 1);
  };

  const handleSubmit = async () => {
    const payload = {
      ...donateInfo,
      franchiseId,
    };
    try {
      await toDonate(payload);
    } catch (error) {
      console.error('Error in donation:', error);
    }
  };

  const handleBackClick = () => {
    if (step > 1) {
      setStep(step - 1);
    }
  };

  return (
    <Layout>
      <header className="flex w-full items-center justify-between px-10 pb-10 pt-4">
        <BackButton onClick={step > 1 ? handleBackClick : undefined} />
        <div className="flex-grow text-center">
          <PageTitle title="후원하기" />
        </div>
        <div className="w-8" />
      </header>

      <main>
        {step === 1 && (
          <Donate1
            onNext={handleNextStep}
            donateInfo={donateInfo}
            setDonateInfo={setDonateInfo}
          />
        )}
        {step === 2 && (
          <Donate2
            onNext={handleNextStep}
            donateInfo={donateInfo}
            setDonateInfo={setDonateInfo}
          />
        )}
        {step === 3 && (
          <Donate3
            onNext={handleNextStep}
            donateInfo={donateInfo}
            setDonateInfo={setDonateInfo}
          />
        )}
        {step === 4 && (
          <Donate4
            onNext={handleNextStep}
            donateInfo={donateInfo}
            setDonateInfo={setDonateInfo}
          />
        )}
        {step === 5 && <Donate5 />}
      </main>
    </Layout>
  );
};

export default DonatePage;
