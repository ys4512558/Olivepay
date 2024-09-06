import { Layout } from '../component/common';
import clsx from 'clsx';

const commonStyle = 'size-4 animate-bounce rounded-full bg-gray-100';

const LoadingPage = () => {
  return (
    <Layout
      className="flex flex-col items-center justify-center bg-PRIMARY"
      hasBottomTab={false}
      isWhite={false}
    >
      <img src="loading_logo.svg" className="size-64" />
      <p className="text-xl font-bold text-gray-100">
        정보를 불러오고 있습니다
      </p>
      <div className="mt-6 flex flex-row gap-2">
        <div className={commonStyle} />
        <div className={clsx(commonStyle, '[animation-delay:-.3s]')} />
        <div className={clsx(commonStyle, '[animation-delay:-.5s]')} />
      </div>
    </Layout>
  );
};

export default LoadingPage;
