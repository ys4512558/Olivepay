import { useEffect } from 'react';
import { useAtom } from 'jotai';
import { useQuery } from '@tanstack/react-query';
import { userAtom } from '../atoms';

import { getUsersInfo } from '../api/userApi';

import { Button, CreditCard, Coupon, PageTitle } from '../component/common';

const Main = () => {
  const handleDownload = () => {
    alert('쿠폰을 다운로드합니다!');
  };

  const [users, setUsers] = useAtom(userAtom);
  const { data, error, isLoading } = useQuery({
    queryKey: ['users'],
    queryFn: getUsersInfo,
  });

  useEffect(() => {
    if (data) {
      setUsers(data);
    }
  }, [data, setUsers]);

  if (isLoading) return <div>로딩 중...</div>;
  if (error) return <div>에러 발생: {error.message}</div>;
  return (
    <>
      <PageTitle title="test" />
      <div>
        {users.slice(0, 5).map((user) => (
          <div key={user.id}>{user.name}</div>
        ))}
      </div>
      <Button />
      <CreditCard
        cardName="꿈나무 8450"
        cardNumber="0000 1111 2222 3333"
        cardOwner="김나무"
      />
      <CreditCard
        cardName="신한 6712"
        cardNumber="110 1111 2222 3333"
        cardOwner="김신한"
      />
      <CreditCard
        cardName="우리 7821"
        cardNumber="1002 445 883 123"
        cardOwner="이우리"
      />
      <CreditCard
        cardName="하나 1423"
        cardNumber="1002 445 883 123"
        cardOwner="최하나"
      />
      <CreditCard
        cardName="국민 9015"
        cardNumber="1002 445 883 123"
        cardOwner="최하나"
      />
      <Coupon
        couponID={1}
        storeName="대우부대찌개"
        cost={2000}
        onClick={handleDownload}
      />
    </>
  );
};

export default Main;
