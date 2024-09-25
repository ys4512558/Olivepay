import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
// import { useQuery } from '@tanstack/react-query';

import {
  Layout,
  NavigateBox,
  // Loader,
  Button,
} from '../../component/common';
import { FranchiseInfo } from '../../component/franchise';
import { franchiseAtom } from '../../atoms';
// import { getStoreInfo } from '../../api/franchiseApi';

import {
  BuildingStorefrontIcon,
  HandThumbUpIcon,
  ChatBubbleLeftEllipsisIcon,
  WalletIcon,
  TicketIcon,
} from '@heroicons/react/24/solid';

const MyStorePage = () => {
  const navigate = useNavigate();
  const [store] = useAtom(franchiseAtom);

  // const franchiseId = 1;

  // const { data, error, isLoading, isSuccess } = useQuery({
  //   queryKey: ['store', franchiseId],
  //   queryFn: () => getStoreInfo(franchiseId),
  // });

  // if (isSuccess && data) {
  //   setStore(data);
  // }

  // if (isLoading) return <Loader />;

  // if (error) return <div>상점 정보 로딩 실패</div>;

  const handleLogout = () => {
    localStorage.clear();
  };

  return (
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
            <img src="/qr_check.svg" className="size-48" />
          </div>
        </section>
        <section
          className="mb-16 bg-LIGHTBASE bg-opacity-50 py-4"
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
  );
};

export default MyStorePage;
