import { useEffect, useState } from 'react';
import { useAtom } from 'jotai';
import { paymentHistoryAtom } from '../atoms/userAtom';
// import { getMyPaymentHistory } from '../api/transactionApi';
import {
  Layout,
  BackButton,
  PageTitle,
  Card,
  Button,
  // Loader,
} from '../component/common';
import { groupByDate } from '../utils/dateUtils';
// import { useQuery } from '@tanstack/react-query';

const PayHistoryPage = () => {
  const [history] = useAtom(paymentHistoryAtom);
  const [index, setIndex] = useState(1);
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    console.log(index);
    setHasMore(false);
  }, []);

  //   const { data, isLoading, error, isSuccess } = useQuery({
  //     queryKey: ['transaction', index],
  //     queryFn: () => getMyPaymentHistory(index),
  //   });

  //   if (isSuccess && data) {
  //     if (data.length < 20) {
  //       setHasMore(false);
  //     } else {
  //       setHistory((prev) => [...prev, ...data]);
  //     }
  //   }

  //   if (isLoading) return <Loader />;

  //   if (error) return <div>결제 내역 조회 실패</div>;

  const groupedHistory = groupByDate(history);
  return (
    <Layout className="px-8">
      <header className="mt-4 flex items-center justify-between">
        <BackButton />
        <PageTitle title="결제 내역" />
        <div className="w-8" />
      </header>
      <main className="mt-4 flex flex-col gap-4">
        {Object.keys(groupedHistory).map((date) => (
          <div key={date}>
            <h2 className="text-md my-4 font-bold text-DARKBASE">{date}</h2>
            <div className="flex flex-col gap-4">
              {groupedHistory[date].map((el) => (
                <Card
                  key={el.transactionId}
                  variant="payment"
                  title={el.franchise?.name || ''}
                  spend={-el.amount}
                  details={el.details}
                />
              ))}
            </div>
          </div>
        ))}
        <div className="mt-4 text-center">
          {hasMore && (
            <Button
              label="더보기"
              variant="secondary"
              onClick={() => setIndex((prev) => prev + 1)}
            />
          )}
        </div>
      </main>
    </Layout>
  );
};

export default PayHistoryPage;
