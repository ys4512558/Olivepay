import { Layout, PageTitle } from '../component/common';
import { useNavigate } from 'react-router-dom';
import {
  MapIcon,
  ChartPieIcon,
  FolderIcon,
  TicketIcon,
} from '@heroicons/react/24/solid';

const DonationInfoPage = () => {
  const navigate = useNavigate();

  const handleMyDonationsClick = () => {
    navigate('/mydonation');
  };
  const handleDonationMap = () => {
    navigate('/map', { state: { status: 'donate' } });
  };

  return (
    <Layout>
      <header className="flex items-center justify-center pb-10 pt-24">
        <div className="text-center">
          <PageTitle title="후원 정보 페이지" />
        </div>
      </header>
      <div className="flex flex-col gap-y-10">
        <section className="">
          <div className="flex flex-col gap-y-4 bg-TERTIARY p-6 text-center">
            <h2 className="text-lg">✨기부금 현황</h2>
            <p className="text-lg">총 기부금: 5,620,000원</p>
            <p>끼니수 : 200</p>
          </div>
        </section>
        <section className="grid grid-cols-2 gap-4 p-4 text-md font-semibold text-DARKBASE">
          <div
            className="flex flex-col items-center gap-y-6 rounded-lg p-2 shadow"
            onClick={handleMyDonationsClick}
          >
            <TicketIcon className="size-8" />
            <span>내 후원 조회하기</span>
          </div>
          <div
            className="flex flex-col items-center gap-y-6 rounded-lg p-2 shadow"
            onClick={handleDonationMap}
          >
            <MapIcon className="size-8" />
            <span>가맹점 조회하기</span>
          </div>
          <div className="flex flex-col items-center gap-y-6 rounded-lg p-2 shadow">
            <ChartPieIcon className="size-8" />
            <span>아동 선호 음식</span>
          </div>
          <div className="flex flex-col items-center gap-y-6 rounded-lg p-2 shadow">
            <FolderIcon className="size-8" />
            <span>관련 기사</span>
          </div>
        </section>
        <footer className="bg-LIGHTBASE p-4 text-center">
          <p className="text-black">캐러셀</p>
        </footer>
      </div>
    </Layout>
  );
};

export default DonationInfoPage;
