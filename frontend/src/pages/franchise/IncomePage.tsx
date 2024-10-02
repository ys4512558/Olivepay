import { useState, useEffect } from 'react';
import { useAtom } from 'jotai';
// import { useQuery } from '@tanstack/react-query';
// import { getFranchiseIncome } from '../../api/transactionApi';
import {
  Layout,
  BackButton,
  PageTitle,
  // Loader,
  Card,
  Button,
} from '../../component/common';
import { franchiseIncomeAtom } from '../../atoms/franchiseAtom';
import { groupByDate } from '../../utils/dateUtils';
import { Helmet } from 'react-helmet';

const IncomePage = () => {
  const [income] = useAtom(franchiseIncomeAtom);
  const [index, setIndex] = useState(1);
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    console.log(index);
    setHasMore(false);
  }, []);

  // const franchiseId = 1;

  //   const { data, isLoading, error, isSuccess } = useQuery({
  //     queryKey: ['transaction', franchiseId, index],
  //     queryFn: () => getFranchiseIncome(franchiseId, index),
  //   });

  //   if (isSuccess && data) {
  //     if (data.length < 20) {
  //       setHasMore(false);
  //     } else {
  //       setIncome((prevIncome) => [...prevIncome, ...data]);
  //     }
  //   }

  //   if (isLoading) return <Loader />;

  //   if (error) return <div>쿠폰 로딩 실패</div>;

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
          {Object.keys(groupedIncome).map((date) => (
            <div key={date}>
              <h2 className="my-4 text-md font-bold text-DARKBASE">{date}</h2>
              <div className="flex flex-col gap-4">
                {groupedIncome[date].map((el) => (
                  <Card
                    key={el.transactionId}
                    variant="payment"
                    title="+"
                    spend={el.amount}
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
    </>
  );
};

export default IncomePage;
