import { Layout, PageTitle, BackButton } from '../component/common';

const DonationInfoPage = () => {
  return (
    <Layout>
      <header className="flex items-center justify-between px-10 pb-10 pt-24">
        <BackButton />
        <div className="flex-grow text-center">
          <PageTitle title="후원 정보 페이지" />
        </div>
        <div className="w-8" />
      </header>
    </Layout>
  );
};

export default DonationInfoPage;
