import { useNavigate, useLocation } from 'react-router-dom';
import {
  PageTitle,
  BackButton,
  Layout,
  Input,
  Button,
} from '../component/common';

const LoginPage: React.FC = () => {
  const navigate = useNavigate();

  const location = useLocation();
  const { loginType } = location.state || {};

  return (
    <Layout hasBottomTab={false}>
      <header className="flex w-full items-center justify-between px-10 pb-10 pt-24">
        <BackButton />
        <div className="flex-grow text-center">
          <PageTitle title="로그인" />
        </div>
        <div className="w-8" />
      </header>
      <main className="p-10">
        <figure className="flex flex-col gap-y-5">
          <Input
            type="id"
            className="col-span-9 border border-gray-300 px-4"
            placeholder="휴대폰번호"
          />
          <Input
            type="password"
            className="col-span-9 border border-gray-300 px-4"
            placeholder="비밀번호"
          />
        </figure>
        <p className="pt-10 text-center text-sm text-gray-400">
          아직 계정이 없으신가요?
          <span
            className="cursor-pointer text-blue-800 underline"
            onClick={() => navigate('/signup', { state: { type: loginType } })}
          >
            회원가입
          </span>
        </p>
        <div className="py-10">
          <Button variant="primary" label="로그인하기" />
        </div>
      </main>
    </Layout>
  );
};

export default LoginPage;
