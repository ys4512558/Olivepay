import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { PageTitle, BackButton } from '../component/common';
import { UserSignUp1, UserSignUp2, UserSignUp3 } from '../component/signup';
import {
  removePhoneFormatting,
  removeBirthdateFormatting,
} from '../helper/utils/formatter';

const SignupPage: React.FC = () => {
  const [formData, setFormData] = useState({
    name: '',
    nickname: '',
    phoneNumber: '',
    userPw: '',
    birthdate: '',
    pin: '',
  });

  const [step, setStep] = useState<number>(1);
  const navigate = useNavigate();

  const handleFormDataChange = (field: string, value: string) => {
    setFormData((prevData) => ({
      ...prevData,
      [field]: value,
    }));
  };

  const handleSubmit = async () => {
    const formattedData = {
      ...formData,
      phoneNumber: removePhoneFormatting(formData.phoneNumber),
      birthdate: removeBirthdateFormatting(formData.birthdate),
    };

    console.log(formattedData);
    navigate('/card');
  };

  const handleBackClick = () => {
    if (step > 1) {
      setStep(step - 1);
    }
  };

  return (
    <>
      <header className="flex w-full items-center justify-between px-10 pb-10 pt-24">
        <BackButton onClick={step > 1 ? handleBackClick : undefined} />
        <div className="flex-grow text-center">
          <PageTitle title="회원가입" />
        </div>
        <div className="w-8" />
      </header>

      {step === 1 && (
        <UserSignUp1
          setStep={setStep}
          handleFormDataChange={handleFormDataChange}
          formData={formData}
        />
      )}
      {step === 2 && (
        <UserSignUp2
          setStep={setStep}
          handleFormDataChange={handleFormDataChange}
          formData={formData}
        />
      )}
      {step === 3 && (
        <UserSignUp3
          setStep={setStep}
          handleFormDataChange={handleFormDataChange}
          formData={formData}
          handleSubmit={handleSubmit}
        />
      )}
    </>
  );
};

export default SignupPage;
