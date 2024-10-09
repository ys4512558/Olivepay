import { useState, useEffect, useCallback } from 'react';
import { useAtom } from 'jotai';
import { useQuery } from '@tanstack/react-query';
import { getFranchiseIncome } from '../../api/transactionApi';
import {
  Layout,
  BackButton,
  PageTitle,
  Loader,
  Card,
  Button,
  EmptyData,
} from '../../component/common';
import { franchiseIncomeAtom } from '../../atoms/franchiseAtom';
import { groupByDate } from '../../utils/dateUtils';
import { Helmet } from 'react-helmet';

const IncomePage = () => {
  const [income, setIncome] = useAtom(franchiseIncomeAtom);
  const [index, setIndex] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  //  로컬 값 연결
  const franchiseId = 2;

  const { data, isLoading, error, isSuccess } = useQuery({
    queryKey: ['transaction', franchiseId],
    queryFn: () => getFranchiseIncome(franchiseId),
  });

  useEffect(() => {
    if (isSuccess && data) {
      setHasMore(data.contents?.length >= 20);
      setIncome(data.contents);
      setIndex(data.nextIndex);
    }
  }, [isSuccess, data, setIncome, setIndex, setHasMore]);

  const handleLoadMore = useCallback(async () => {
    const result = await getFranchiseIncome(index);
    if (result.contents.length < 20) {
      setHasMore(false);
    }
    setIndex(result.nextIndex);
    setIncome((prev) => [...prev, ...result.contents]);
  }, [index, setIncome]);

  if (isLoading) return <Loader />;

  if (error) return <div>결제 내역 로딩 실패</div>;

  const groupedIncome = groupByDate(income);

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="가맹점주가 자신의 가맹점에 대해 수입 내역을 확인할 수 있습니다."
        />
      </Helmet>
      <Layout className="px-8">
        <header className="mt-4 flex items-center justify-between">
          <BackButton />
          <PageTitle title="가맹점 결제 내역" />
          <div className="w-8" />
        </header>
        <main className="mt-4 flex flex-col gap-4">
          {income.length === 0 && <EmptyData label="결제 내역이 없습니다." />}
          {Object.keys(groupedIncome).map((date) => (
            <div key={date}>
              <h2 className="my-4 text-md font-bold text-DARKBASE">{date}</h2>
              <div className="flex flex-col gap-4">
                {groupedIncome[date].map((el) => (
                  <Card
                    key={el.paymentId}
                    variant="payment"
                    title="+"
                    spend={el.amount}
                    details={el.details}
                  />
                ))}
              </div>
            </div>
          ))}
          <div className="mb-24 text-center">
            {hasMore && (
              <Button
                label="더보기"
                variant="secondary"
                onClick={handleLoadMore}
              />
            )}
          </div>
        </main>
      </Layout>
    </>
  );
};

export default IncomePage;
