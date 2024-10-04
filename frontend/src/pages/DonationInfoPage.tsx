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
import { getDonationInfo, getFundingUsages } from '../api/donationApi';
import { News, UseFavoriteCategory } from '../component/donate';
import Slider from 'react-slick';

import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

const DonationInfoPage = () => {
  const navigate = useNavigate();
  const [donationInfo, setDonationInfo] = useState({ mealCount: 0, total: 0 });
  const [loading, setLoading] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState<
    'news' | 'userFavorite' | null
  >(null);
  const [fundingUsages, setFundingUsages] = useState([]);
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
          <NavigateBox
            className="flex flex-col items-center gap-y-6 rounded-xl border-2 p-2 shadow-md"
            path=""
            icon={<ChartPieIcon className="size-8" />}
            onClick={() => openModal('userFavorite')}
            text="아동 선호 음식"
          />
          <div>
            <NavigateBox
              className="flex flex-col items-center gap-y-6 rounded-xl border-2 p-2 shadow-md"
              path=""
              icon={<FolderIcon className="size-8" />}
              onClick={() => openModal('news')}
              text="관련기사"
            />
          </div>
        </section>
        <footer className="mb-32 p-4">
          {fundingUsages.length > 0 ? (
            <Slider {...settings}>
              {fundingUsages.map((usage, index) => (
                <div key={index}>
                  <Card
                    variant="restaurant"
                    title={usage.organization}
                    price={usage.amount}
                  />
                </div>
              ))}
            </Slider>
          ) : (
            <p className="text-base">사용 내역이 없습니다.</p>
          )}
        </footer>
      </div>
      <Modal isOpen={isModalOpen} onClose={closeModal}>
        {modalContent === 'news' && <News />}
        {modalContent === 'userFavorite' && <UseFavoriteCategory />}
      </Modal>
    </Layout>
  );
};

export default DonationInfoPage;
