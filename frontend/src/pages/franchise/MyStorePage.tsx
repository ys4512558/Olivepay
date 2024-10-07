import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import { useQuery } from '@tanstack/react-query';

import { Layout, NavigateBox, Loader, Button } from '../../component/common';
import { FranchiseInfo } from '../../component/franchise';
import { franchiseAtom } from '../../atoms';
import { getFranchiseDetail } from '../../api/franchiseApi';

import {
  BuildingStorefrontIcon,
  HandThumbUpIcon,
  ChatBubbleLeftEllipsisIcon,
  WalletIcon,
  TicketIcon,
} from '@heroicons/react/24/solid';
import { Helmet } from 'react-helmet';
import { logout } from '../../api/loginApi';
import { useSnackbar } from 'notistack';
import Cookies from 'js-cookie';

const MyStorePage = () => {
  const { enqueueSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const [store, setStore] = useAtom(franchiseAtom);

  const franchiseId = +(localStorage.getItem('franchiseId') || 0);

  const { data, error, isLoading, isSuccess } = useQuery({
    queryKey: ['store', franchiseId],
    queryFn: () => getFranchiseDetail(franchiseId),
  });

  if (isSuccess && data) {
    setStore(data);
  }

  if (isLoading) return <Loader />;

  if (error) return <div>상점 정보 로딩 실패</div>;

  const handleLogout = async () => {
    try {
      await logout();
    } catch {
      enqueueSnackbar('로그아웃 요청이 실패했습니다. 토큰을 초기화합니다.', {
        variant: 'warning',
      });
    } finally {
      localStorage.clear();
      Cookies.remove('refreshToken');
      enqueueSnackbar('로그아웃 되었습니다', { variant: 'info' });
      navigate('/');
    }
  };

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="가맹점주가 자신의 가맹점에 대한 정보를 조회하고 이용할 수 있는 서비스로 이동할 수 있습니다."
        />
      </Helmet>
      <Layout>
        <header className="pb-4 text-end text-base">
          <Button
            className="mt-4"
            variant="text"
            label="로그아웃"
            onClick={handleLogout}
          />
          <FranchiseInfo
            className="mt-4 text-center"
            franchiseName={store.franchiseName}
            category={store.category}
          />
        </header>
        <main>
          <section
            className="bg-LIGHTBASE bg-opacity-50 p-4"
            style={{
              boxShadow:
                '0 -4px 6px -1px rgba(0, 0, 0, 0.1), 0 -2px 4px -1px rgba(0, 0, 0, 0.06), 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
            }}
          >
            <div className="flex h-12 items-center justify-center gap-2 rounded-lg bg-white text-base shadow-md">
              <BuildingStorefrontIcon className="size-6 text-PRIMARY" />
              {store.address}
            </div>
            <div className="mt-2 flex gap-2">
              <NavigateBox
                className="h-26 flex-grow-[3] bg-white"
                path="/franchise/coupon"
                icon={<TicketIcon className="size-7 text-PRIMARY" />}
                text="보유 쿠폰"
                bigger={true}
              />
              <div className="flex flex-grow flex-col gap-2 text-base">
                <div className="flex h-12 items-center justify-center gap-2 rounded-lg bg-white p-2 shadow-md">
                  <HandThumbUpIcon className="size-5 text-PRIMARY" />
                  {store.likes}
                </div>
                <div className="flex h-12 items-center justify-center gap-2 rounded-lg bg-white p-2 shadow-md">
                  <ChatBubbleLeftEllipsisIcon className="size-5 text-PRIMARY" />
                  {store.reviews}
                </div>
              </div>
            </div>
          </section>
          <section className="pb-4">
            <h2 className="mb-4 pt-4 text-center text-xl font-bold text-DARKBASE">
              QR 생성
            </h2>
            <div
              className="mt-4 flex justify-center"
              onClick={() => navigate('/franchise/qr')}
            >
              <img src="/image/qr_check.svg" className="size-48" />
            </div>
          </section>
          <section
            className="mb-20 bg-LIGHTBASE bg-opacity-50 py-4"
            style={{
              boxShadow:
                '0 -4px 6px -1px rgba(0, 0, 0, 0.1), 0 -2px 4px -1px rgba(0, 0, 0, 0.06), 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
            }}
          >
            <NavigateBox
              className="mx-8 bg-white"
              path="/franchise/income"
              text="결제 내역 조회"
              icon={<WalletIcon className="size-7 text-PRIMARY" />}
            />
          </section>
        </main>
      </Layout>
    </>
  );
};

export default MyStorePage;
