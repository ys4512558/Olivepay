import { Layout } from '../component/common';
import clsx from 'clsx';
import { Helmet } from 'react-helmet';

const commonStyle = 'size-4 animate-bounce rounded-full bg-gray-100';

const LoadingPage = () => {
  return (
    <>
      <Helmet>
        <meta name="description" content="페이지 이동에서의 대기 화면입니다." />
      </Helmet>
      <Layout
        className="flex flex-col items-center justify-center bg-PRIMARY"
        hasBottomTab={false}
        isWhite={false}
      >
        <img src="/image/loading_logo.svg" className="size-64" />
        <p className="text-xl font-bold text-gray-100">
          정보를 불러오고 있습니다
        </p>
        <div className="mt-6 flex flex-row gap-2">
          <div className={commonStyle} />
          <div className={clsx(commonStyle, '[animation-delay:-.3s]')} />
          <div className={clsx(commonStyle, '[animation-delay:-.5s]')} />
        </div>
      </Layout>
    </>
  );
};

export default LoadingPage;
