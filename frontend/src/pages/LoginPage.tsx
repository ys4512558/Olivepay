import { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import {
  PageTitle,
  BackButton,
  Layout,
  Input,
  Button,
} from '../component/common';
import { Helmet } from 'react-helmet';
import { userLogin, franchiserLogin } from '../api/loginApi';
import { formatPhoneNumber } from '../utils/validators';
import { removePhoneFormatting } from '../utils/formatter';
import { useSnackbar } from 'notistack';
import axios from 'axios';

const LoginPage: React.FC = () => {
  const { enqueueSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const location = useLocation();
  const { loginType } = location.state || {};
  const [phoneNumber, setPhoneNumber] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (event: React.FormEvent) => {
    event.preventDefault();
    const originalPhoneNumber = removePhoneFormatting(phoneNumber);
    try {
      let response;
      console.log('Login Type:', loginType);
      console.log('Phone Number:', phoneNumber);
      console.log('Password:', password);

      if (loginType === 'for_user') {
        response = await userLogin(originalPhoneNumber, password);
      } else if (loginType === 'for_franchiser') {
        response = await franchiserLogin(originalPhoneNumber, password);
      }
      if (response) {
        console.log(response);
        enqueueSnackbar(
          `${response.message || '알 수 없는 오류가 발생했습니다.'}`,
          {
            variant: 'success',
          },
        );
        if (loginType === 'for_user') {
          navigate('/home');
        } else if (loginType === 'for_franchiser') {
          navigate('/franchise/home');
        }
      }
    } catch (error) {
      if (axios.isAxiosError(error)) {
        if (error.response && error.response.data) {
          console.log(error);
          if (error.response.data.code === 'BAD_REQUEST') {
            enqueueSnackbar(
              `${error.response.data.data || '알 수 없는 오류가 발생했습니다.'}`,
              {
                variant: 'error',
              },
            );
          }
        }
      }
    }
  };

  const handlePhoneNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value;
    const formattedPhoneNumber = formatPhoneNumber(inputValue); // 포맷 적용
    setPhoneNumber(formattedPhoneNumber);
  };

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="결식 아동과 가맹점주가 로그인을 할 수 있습니다."
        />
      </Helmet>
      <Layout hasBottomTab={false}>
        <header className="flex w-full items-center justify-between px-10 pb-10 pt-4">
          <BackButton />
          <div className="flex-grow text-center">
            <PageTitle title="로그인" />
          </div>
          <div className="w-8" />
        </header>
        <main className="p-5">
          <form onSubmit={handleLogin}>
            <figure className="flex flex-col gap-y-5">
              <Input
                type="text"
                className="col-span-9 border border-gray-300 px-4"
                placeholder="휴대폰번호"
                value={phoneNumber}
                onChange={handlePhoneNumberChange}
                maxLength={13}
              />
              <Input
                type="password"
                className="col-span-9 border border-gray-300 px-4"
                placeholder="비밀번호"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                maxLength={16}
              />
            </figure>
            <p className="pt-10 text-center text-sm text-gray-400">
              아직 계정이 없으신가요?
              <span
                className="cursor-pointer text-blue-800 underline"
                onClick={() =>
                  navigate('/signup', { state: { type: loginType } })
                }
              >
                회원가입
              </span>
            </p>
            <div className="py-10">
              <Button variant="primary" label="로그인하기" type="submit" />
            </div>
          </form>
        </main>
      </Layout>
    </>
  );
};

export default LoginPage;
