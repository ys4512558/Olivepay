import { useState } from 'react';
import { Layout, PageTitle, BackButton } from '../component/common';
import { GetDonationList, MyDonationList } from '../component/donate';

const MyDonationPage = () => {
  const [step, setStep] = useState(1);
  const [email, setEmail] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  // const [donationList, setDonationList] = useState([]);

  const title = step === 1 ? '내 후원 조회하기' : '내 후원 목록';

  const handleNextStep = () => {
    setStep(step + 1);
  };

  const handleBackClick = () => {
    if (step > 1) {
      setStep(step - 1);
    }
  };

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
        {step === 1 && (
          <GetDonationList
            onNext={handleNextStep}
            // onNext={fetchDonationList}
            email={email}
            setEmail={setEmail}
            phoneNumber={phoneNumber}
            setPhoneNumber={setPhoneNumber}
          />
        )}
        {step === 2 && (
          <MyDonationList
          // donationList={donationList}
          />
        )}
      </div>
    </Layout>
  );
};

export default MyDonationPage;
