import { useState } from 'react';
import { Layout, PageTitle, BackButton } from '../component/common';
import {
  Donate1,
  Donate2,
  Donate3,
  Donate4,
  Donate5,
} from '../component/donate';

const DonatePage = () => {
  const [step, setStep] = useState(1);
  const [name, setName] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [email, setEmail] = useState('');
  const [franchiseId, setFranchiseId] = useState('1');
  const [count2000, setCount2000] = useState(0);
  const [count4000, setCount4000] = useState(0);
  const [couponMessage, setCouponMessage] = useState('');
  const [accountNumber, setAccountNumber] = useState('');

  const amount = count2000 * 2000 + count4000 * 4000;

  const handleNextStep = () => {
    if (step === 2) {
      if (amount <= 0) {
        alert('금액을 입력해주세요.');
        return;
      }
      if (!couponMessage.trim()) {
        alert('쿠폰 멘트를 입력해주세요.');
        return;
      }
      console.log(count2000, count4000, couponMessage);
    }
    if (step === 4) {
      handleSubmit();
    }
    setStep(step + 1);
  };

  const handleSubmit = () => {
    const payload = {
      name,
      phoneNumber,
      email,
      franchiseId,
      amount,
      message: couponMessage,
      coupon2000: count2000,
      coupon4000: count4000,
      accountNumber,
    };
    console.log('DATA:', payload);
    //  API 호출
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
          <PageTitle title="후원하기" />
        </div>
        <div className="w-8" />
      </header>

      <main>
        {step === 1 && (
          <Donate1
            onNext={handleNextStep}
            name={name}
            setName={setName}
            phoneNumber={phoneNumber}
            setPhoneNumber={setPhoneNumber}
            email={email}
            setEmail={setEmail}
          />
        )}
        {step === 2 && (
          <Donate2
            onNext={handleNextStep}
            count2000={count2000}
            setCount2000={setCount2000}
            count4000={count4000}
            setCount4000={setCount4000}
            couponMessage={couponMessage}
            setCouponMessage={setCouponMessage}
            amount={amount}
          />
        )}
        {step === 3 && (
          <Donate3
            onNext={handleNextStep}
            accountNumber={accountNumber}
            setAccountNumber={setAccountNumber}
          />
        )}
        {step === 4 && (
          <Donate4
            onNext={handleNextStep}
            amount={amount}
            accountNumber={accountNumber}
            count2000={count2000}
            count4000={count4000}
            couponMessage={couponMessage}
          />
        )}
        {step === 5 && <Donate5 />}
      </main>
    </Layout>
  );
};

export default DonatePage;
