import { useEffect, useState } from 'react';
import { Layout, PageTitle, NavigateBox, Modal } from '../component/common';
import { useNavigate } from 'react-router-dom';
import {
  MapIcon,
  ChartPieIcon,
  FolderIcon,
  TicketIcon,
} from '@heroicons/react/24/solid';
import { getDonationInfo } from '../api/donationApi';

const DonationInfoPage = () => {
  const navigate = useNavigate();
  const [donationInfo, setDonationInfo] = useState({ mealCount: 0, total: 0 });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDonationInfo = async () => {
      try {
        const response = await getDonationInfo();
        setDonationInfo(response.data);
      } catch (error) {
        console.error('Error fetching donation info:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchDonationInfo();
  }, []);

  const handleMyDonationsClick = () => {
    navigate('/mydonation');
  };
  const handleDonationMap = () => {
    navigate('/map', { state: { status: 'donate' } });
  };
  const handleChildrensFavorite = () => {
    navigate('/map');
  };
  return (
    <Layout>
      <header className="flex items-center justify-center pb-10 pt-6">
        <div className="text-center">
          <PageTitle title="후원 정보 페이지" />
        </div>
      </header>
      <div className="flex flex-col gap-y-8">
        <section className="">
          <div className="flex flex-col gap-y-4 bg-TERTIARY p-6 text-center text-white">
            <h2 className="text-lg font-bold">✨기부금 현황</h2>
            {loading ? (
              <p>로딩 중...</p>
            ) : (
              <>
                <p className="text-base">
                  지금까지
                  <span className="ps-1 text-lg font-semibold">
                    {donationInfo.total.toLocaleString()}원
                  </span>
                  의 쿠폰이 모였어요.
                </p>
                <p className="text-base">
                  아이들의
                  <span className="ps-1 text-lg font-semibold">
                    {donationInfo.mealCount}번
                  </span>
                  의 끼니를 함께했어요.
                </p>
              </>
            )}
          </div>
        </section>
        <section className="grid grid-cols-2 gap-4 p-4 text-base font-semibold text-DARKBASE">
          <div
            className="flex flex-col items-center gap-y-6 rounded-xl border-2 p-2 shadow-md"
            onClick={handleMyDonationsClick}
          >
            <TicketIcon className="size-8" />
            <span>내 후원 조회하기</span>
          </div>
          <div
            className="flex flex-col items-center gap-y-6 rounded-xl border-2 p-2 shadow-md"
            onClick={handleDonationMap}
          >
            <MapIcon className="size-8" />
            <span>가맹점 조회하기</span>
          </div>
          <div
            className="flex flex-col items-center gap-y-6 rounded-xl border-2 p-2 shadow-md"
            onClick={handleChildrensFavorite}
          >
            <ChartPieIcon className="size-8" />
            <span>아동 선호 음식</span>
          </div>
          </div>
        </section>
        <footer className="mb-32 bg-LIGHTBASE p-4 text-center">
          <p className="text-black">캐러셀</p>
        </footer>
      </div>
    </Layout>
  );
};

export default DonationInfoPage;
