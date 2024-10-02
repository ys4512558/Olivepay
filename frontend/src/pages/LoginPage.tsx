import { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import {
  PageTitle,
  BackButton,
  Layout,
  Input,
  Button,
} from '../component/common';
import { userLogin, franchiserLogin } from '../api/loginApi';
import { formatPhoneNumber } from '../utils/validators';
import { removePhoneFormatting } from '../utils/formatter';

const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { loginType } = location.state || {};
  const [phoneNumber, setPhoneNumber] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);

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

        navigate('/');
      }
    } catch (error) {
      console.error('Login Failed:', error);
      setError('로그인 실패: 사용자 정보를 확인하세요.');
    }
  };

  const handlePhoneNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value;
    const formattedPhoneNumber = formatPhoneNumber(inputValue); // 포맷 적용
    setPhoneNumber(formattedPhoneNumber);
  };

  return (
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
            />
            <Input
              type="password"
              className="col-span-9 border border-gray-300 px-4"
              placeholder="비밀번호"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </figure>
          <p className="py-3">
            {error && <p className="ml-3 text-sm text-red-500">{error}</p>}
          </p>
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
  );
};

export default LoginPage;
