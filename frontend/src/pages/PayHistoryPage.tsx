import { useState } from 'react';
import { useAtom } from 'jotai';
import { paymentHistoryAtom } from '../atoms/userAtom';
import { getMyPaymentHistory } from '../api/transactionApi';
import {
  Layout,
  BackButton,
  PageTitle,
  Card,
  Button,
  Loader,
} from '../component/common';
import { groupByDate } from '../utils/dateUtils';
import { useQuery } from '@tanstack/react-query';
import { Helmet } from 'react-helmet';

const PayHistoryPage = () => {
  const [history, setHistory] = useAtom(paymentHistoryAtom);
  const [index, setIndex] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  const { data, isLoading, error, isSuccess } = useQuery({
    queryKey: ['transaction', index],
    queryFn: () => {
      return index ? getMyPaymentHistory(index) : getMyPaymentHistory();
    },
  });

  if (isSuccess && data) {
    if (data.length < 20) {
      setHasMore(false);
    } else {
      setHistory((prev: paymentList) => [...prev, ...data.history]);
    }
    setIndex(data.nextIndex);
  }

  if (isLoading) return <Loader />;

  if (error) return <div>결제 내역 조회 실패</div>;

  const groupedHistory = groupByDate(history);

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="결식 아동의 결제 내역을 확인할 수 있습니다."
        />
      </Helmet>
      <Layout className="px-8">
        <header className="mt-4 flex items-center justify-between">
          <BackButton />
          <PageTitle title="결제 내역" />
          <div className="w-8" />
        </header>
        <main className="mt-4 flex flex-col gap-4">
          {Object.keys(groupedHistory).map((date) => (
            <div key={date}>
              <h2 className="my-4 text-md font-bold text-DARKBASE">{date}</h2>
              <div className="flex flex-col gap-4">
                {groupedHistory[date].map((el) => (
                  <Card
                    key={el.paymentId}
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
                onClick={() => getMyPaymentHistory(index)}
              />
            )}
          </div>
        </main>
      </Layout>
    </>
  );
};

export default PayHistoryPage;
