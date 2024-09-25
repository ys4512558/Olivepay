import { useState } from 'react';
import { Layout, PageTitle, BackButton } from '../component/common';
import { GetDonationList, MyDonationList } from '../component/donate';

const MyDonationPage = () => {
  const [step, setStep] = useState(1);

  const handleNextStep = () => {
    setStep(step + 1);
  };

  const handleBackClick = () => {
    if (step > 1) {
      setStep(step - 1);
    }
  };

  const title = step === 1 ? '내 후원 조회하기' : '내 후원 목록';

  return (
    <Layout>
      <header className="flex w-full items-center justify-between px-10 pb-10 pt-24">
        <BackButton onClick={step > 1 ? handleBackClick : undefined} />
        <div className="flex-grow text-center">
          <PageTitle title={title} />
        </div>
        <div className="w-8" />
      </header>

      <div className="flex flex-col gap-y-10">
        {step === 1 && <GetDonationList onNext={handleNextStep} />}
        {step === 2 && <MyDonationList />}
      </div>
    </Layout>
  );
};

export default MyDonationPage;
