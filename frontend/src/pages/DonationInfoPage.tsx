import { useEffect, useState } from 'react';
import {
  Layout,
  PageTitle,
  NavigateBox,
  Modal,
  Card,
} from '../component/common';
import { useNavigate } from 'react-router-dom';
import {
  MapIcon,
  ChartPieIcon,
  FolderIcon,
  TicketIcon,
} from '@heroicons/react/24/solid';
import { Helmet } from 'react-helmet';
import { getDonationInfo, getFundingUsages } from '../api/donationApi';
import { News, UseFavoriteCategory } from '../component/donate';
import Slider from 'react-slick';

import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

type FundingUsage = {
  organization: string;
  amount: number;
};

const DonationInfoPage = () => {
  const navigate = useNavigate();
  const [donationInfo, setDonationInfo] = useState({ mealCount: 0, total: 0 });
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState<
    'news' | 'userFavorite' | null
  >(null);
  const [fundingUsages, setFundingUsages] = useState<FundingUsage[]>([]);
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 3000,
  };

  const openModal = (contentType: 'news' | 'userFavorite') => {
    setModalContent(contentType);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setModalContent(null);
  };

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

  useEffect(() => {
    const fetchFundingUsages = async () => {
      try {
        const response = await getFundingUsages();
        setFundingUsages(response.data);
      } catch (error) {
        console.error('Error fetching funding usages:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchFundingUsages();
  }, []);

  const handleMyDonationsClick = () => {
    navigate('/mydonation');
  };
  const handleDonationMap = () => {
    navigate('/map', { state: { status: 'donate' } });
  };
  return (
    <>
      <Helmet>
        <meta name="description" content="총 후원 통계를 확인할 수 있습니다." />
      </Helmet>
      <Layout>
        <header className="flex items-center justify-center bg-white pb-10 pt-6 shadow-sm">
          <div className="text-center">
            <PageTitle title="후원 정보 페이지" />
          </div>
        </header>
        <div className="flex flex-col gap-y-6 px-4">
          <section className="p-2" title="donations">
            <div className="flex flex-col gap-y-4 rounded-xl bg-gradient-to-r from-PRIMARY to-TERTIARY p-6 text-center text-white shadow-lg">
              <h2 className="text-2xl font-extrabold">✨ 기부금 현황</h2>
              {loading ? (
                <p className="text-lg font-semibold">로딩 중...</p>
              ) : (
                <div className="grid grid-cols-2 gap-4">
                  <div className="flex flex-col items-center justify-center rounded-lg bg-white p-4 shadow-md">
                    <p className="text-base text-gray-500">
                      지금까지 모인 금액
                    </p>
                    <p className="mt-2 text-xl font-bold text-DARKBASE">
                      {donationInfo.total.toLocaleString()}원
                    </p>
                  </div>
                  <div className="flex flex-col items-center justify-center rounded-lg bg-white p-4 shadow-md">
                    <p className="text-base text-gray-500">아이들의 끼니 수</p>
                    <p className="mt-2 text-xl font-bold text-DARKBASE">
                      {donationInfo.mealCount}번
                    </p>
                  </div>
                </div>
              )}
            </div>
          </section>

          <section
            title="menus"
            className="grid grid-cols-2 gap-4 p-4 text-base font-semibold text-DARKBASE"
          >
            <div
              className="flex flex-col items-center justify-center gap-3 gap-y-6 rounded-xl border-2 p-2 shadow-md"
              onClick={handleMyDonationsClick}
            >
              <TicketIcon className="size-10 text-PRIMARY" />
              <span>내 후원 조회하기</span>
            </div>
            <div
              className="flex flex-col items-center justify-center gap-3 gap-y-6 rounded-xl border-2 p-2 shadow-md"
              onClick={handleDonationMap}
            >
              <MapIcon className="size-10 text-PRIMARY" />
              <span>가맹점 조회하기</span>
            </div>
            <NavigateBox
              className="flex flex-col items-center justify-center gap-3 gap-y-6 rounded-xl border-2 p-2 shadow-md"
              path=""
              icon={<ChartPieIcon className="size-10 text-PRIMARY" />}
              onClick={() => openModal('userFavorite')}
              text="아동 선호 음식"
            />
            <div>
              <NavigateBox
                className="'flex shadow-md' flex flex-col items-center justify-center gap-3 gap-y-6 rounded-xl border-2 p-2"
                path=""
                icon={<FolderIcon className="size-10 text-PRIMARY" />}
                onClick={() => openModal('news')}
                text="관련기사"
              />
            </div>
          </section>

          <footer className="mb-32 px-4">
            <p className="p-3 text-lg font-bold text-DARKBASE">
              공용기부금 사용내역
            </p>
            <div>
              {fundingUsages.length > 0 ? (
                <Slider {...settings}>
                  {fundingUsages.map((usage, index) => (
                    <div key={index}>
                      <Card
                        variant="info"
                        title={usage.organization}
                        price={usage.amount}
                      />
                    </div>
                  ))}
                </Slider>
              ) : (
                <p className="text-base text-gray-500">사용 내역이 없습니다.</p>
              )}
            </div>
          </footer>
        </div>
        <Modal isOpen={isModalOpen} onClose={closeModal}>
          {modalContent === 'news' && <News />}
          {modalContent === 'userFavorite' && <UseFavoriteCategory />}
        </Modal>
      </Layout>
    </>
  );
};

export default DonationInfoPage;
