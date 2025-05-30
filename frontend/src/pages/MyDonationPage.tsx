import { useState, useEffect } from 'react';
import { Layout, PageTitle, BackButton } from '../component/common';
import { GetDonationList, MyDonationList } from '../component/donate';
import { Helmet } from 'react-helmet';
import { getMyDonations } from '../api/donationApi';
import { removePhoneFormatting } from '../utils/formatter'

const MyDonationPage = () => {
  const [step, setStep] = useState(1);
  const [email, setEmail] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [donationList, setDonationList] = useState([]);

  const title = step === 1 ? '내 후원 조회하기' : '내 후원 목록';

  const handleNextStep = () => {
    setStep(step + 1);
  };

  const handleBackClick = () => {
    if (step > 1) {
      setStep(step - 1);
    }
  };

  useEffect(() => {
  }, [donationList]);
  
  const getDonationList = async () => {
    const formattedPhone = removePhoneFormatting(phoneNumber)
    try {
      const donations = await getMyDonations(email, formattedPhone);
      setDonationList(donations.data.contents); 
      handleNextStep();
    } catch (error) {
      console.error('Error fetching donations:', error);
      alert('후원 목록을 불러오는 중 오류가 발생했습니다.');
    }
  };

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="나의 후원 내역을 확인할 수 있습니다."
        />
      </Helmet>
      <Layout>
        <header className="flex w-full items-center justify-between px-10 pb-10 pt-4">
          <BackButton onClick={step > 1 ? handleBackClick : undefined} />
          <div className="flex-grow text-center">
            <PageTitle title={title} />
          </div>
          <div className="w-8" />
        </header>

        <div className="flex flex-col gap-y-10">
          {step === 1 && (
            <GetDonationList
              onNext={getDonationList}
              email={email}
              setEmail={setEmail}
              phoneNumber={phoneNumber}
              setPhoneNumber={setPhoneNumber}
            />
          )}
          {step === 2 && <MyDonationList donationList={donationList} />}
        </div>
      </Layout>
    </>
  );
};

export default MyDonationPage;
