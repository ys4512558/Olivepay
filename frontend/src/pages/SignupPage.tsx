import { useNavigate, useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { PageTitle, BackButton, Layout } from '../component/common';
import {
  UserSignUp1,
  UserSignUp2,
  UserSignUp3,
  UserSignUp4,
} from '../component/signup';
import {
  removePhoneFormatting,
  removeBirthdateFormatting,
  removeTelePhoneFormatting,
} from '../utils/formatter';
import { Helmet } from 'react-helmet';
import { userSignUp, franchiserSignUp } from '../api/signUpApi';
import { userLogin } from '../api/loginApi';
import { useSnackbar } from 'notistack';
import axios from 'axios';

const SignupPage: React.FC = () => {
  const { enqueueSnackbar } = useSnackbar();
  const [formData1, setFormData1] = useState({
    name: '',
    phoneNumber: '',
    password: '',
    nickname: '',
    birthdate: '',
    pin: '',
  });

  const [formData2, setFormData2] = useState({
    name: '',
    phoneNumber: '',
    password: '',
    telephoneNumber: '',
    franchiseName: '',
    registrationNumber: '',
    address: '',
    category: '',
    rrnPrefix: '',
    rrnCheckDigit: '',
    longitude: 0,
    latitude: 0,
  });

  const [step, setStep] = useState<number>(1);
  const navigate = useNavigate();
  const location = useLocation();
  const [signupType, setSignupType] = useState<string>('for_user');

  useEffect(() => {
    if (location.state && location.state.type) {
      setSignupType(location.state.type);
    }
  }, [location.state]);

  const handleFormDataChange = (
    field: string,
    value: string,
    formType: 'formData1' | 'formData2',
  ) => {
    if (formType === 'formData1') {
      setFormData1((prevData) => ({
        ...prevData,
        [field]: value,
      }));
    } else {
      setFormData2((prevData) => ({
        ...prevData,
        [field]: value,
      }));
    }
  };

  const handleSubmit = async () => {
    try {
      let formattedData;
      if (signupType === 'for_user') {
        formattedData = {
          ...formData1,
          phoneNumber: removePhoneFormatting(formData1.phoneNumber),
          birthdate: removeBirthdateFormatting(formData1.birthdate),
        };
        const signUpResponse = await userSignUp(formattedData);
        await userLogin(formattedData.phoneNumber, formattedData.password);
        enqueueSnackbar(`${signUpResponse?.message}`, {
          variant: 'success',
        });
        navigate('/card', {
          state: {
            phoneNumber: formattedData.phoneNumber,
            password: formattedData.password,
          },
        });
      } else if (signupType === 'for_franchiser') {
        formattedData = {
          ...formData2,
          phoneNumber: removePhoneFormatting(formData2.phoneNumber),
          telephoneNumber: removeTelePhoneFormatting(formData2.telephoneNumber),
        };
        const franchiserResponse = await franchiserSignUp(formattedData);
        enqueueSnackbar(`${franchiserResponse?.message}`, {
          variant: 'success',
        });
        navigate('/login', { state: { loginType: 'for_franchiser' } });
      }
    } catch (error: unknown) {
      enqueueSnackbar('회원가입에 실패했습니다.', {
        variant: 'error',
      });

      if (axios.isAxiosError(error)) {
        if (error.status === 400) {
          enqueueSnackbar(
            `${error.response?.data?.data || '알 수 없는 오류가 발생했습니다.'}`,
            {
              variant: 'error',
            },
          );
        } else {
          enqueueSnackbar(
            `${error.response?.data?.message || '알 수 없는 오류가 발생했습니다.'}`,
            {
              variant: 'error',
            },
          );
        }
      } else {
        enqueueSnackbar('알 수 없는 오류가 발생했습니다.', {
          variant: 'error',
        });
      }
    }
  };

  const handleBackClick = () => {
    if (step > 1) {
      setStep(step - 1);
    }
  };

  const handleNextStep = () => {
    if (signupType === 'for_user') {
      if (step < 3) {
        setStep(step + 1);
      }
    } else if (signupType === 'for_franchiser') {
      if (step === 1) {
        setStep(4);
      }
    }
  };

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="결식 아동(일반 유저) 또는 가맹점주로 회원가입을 할 수 있습니다."
        />
      </Helmet>
      <Layout>
        <header className="flex w-full items-center justify-between px-10 pb-5 pt-4">
          <BackButton onClick={step > 1 ? handleBackClick : undefined} />
          <div className="flex-grow text-center">
            <PageTitle title="회원가입" />
          </div>
          <div className="w-8" />
        </header>

        {step === 1 && (
          <UserSignUp1
            setStep={handleNextStep}
            handleFormDataChange={handleFormDataChange}
            formData1={formData1}
            formData2={formData2}
            signupType={signupType}
          />
        )}

        {signupType === 'for_user' && step === 2 && (
          <UserSignUp2
            setStep={handleNextStep}
            handleFormDataChange={handleFormDataChange}
            formData1={formData1}
            formData2={formData2}
            signupType={signupType}
          />
        )}

        {signupType === 'for_user' && step === 3 && (
          <UserSignUp3
            setStep={handleNextStep}
            handleFormDataChange={handleFormDataChange}
            formData1={formData1}
            formData2={formData2}
            handleSubmit={handleSubmit}
            signupType={signupType}
          />
        )}

        {signupType === 'for_franchiser' && step === 4 && (
          <UserSignUp4
            setStep={handleNextStep}
            handleFormDataChange={handleFormDataChange}
            formData1={formData1}
            formData2={formData2}
            handleSubmit={handleSubmit}
            signupType={signupType}
          />
        )}
      </Layout>
    </>
  );
};

export default SignupPage;
