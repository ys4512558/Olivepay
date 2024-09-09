import { useNavigate } from 'react-router-dom';

import { Button, Layout } from '../component/common';

const NotFoundPage = () => {
  const navigate = useNavigate();
  return (
    <Layout
      className="flex flex-col items-center justify-center bg-PRIMARY"
      hasBottomTab={false}
      isWhite={false}
    >
      <p className="text-xl font-bold text-gray-100">존재하지 않는 페이지</p>
      <img src="404_logo.svg" className="size-64" />
      <Button
        variant="text"
        className="mt-4 text-lg font-semibold text-gray-100"
        label="돌아가기"
        onClick={() => navigate(-1)}
      />
    </Layout>
  );
};

export default NotFoundPage;
