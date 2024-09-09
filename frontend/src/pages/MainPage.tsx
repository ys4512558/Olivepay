import { useEffect } from 'react';
import { useAtom } from 'jotai';
import { useQuery } from '@tanstack/react-query';
import { userAtom, inputAtom } from '../atoms'; // inputAtom을 추가로 불러옴

import { getUsersInfo } from '../api/userApi';
import {
  Layout,
  Button,
  BackButton,
  Input,
  Loader,
  Card,
  CreditCard,
  Coupon,
  PageTitle,
  KeyPad,
} from '../component/common';
import Stepper from '../component/common/Stepper';

const MainPage = () => {
  const [users, setUsers] = useAtom(userAtom);
  const [inputValue, setInputValue] = useAtom(inputAtom);

  const { data, error, isLoading } = useQuery({
    queryKey: ['users'],
    queryFn: getUsersInfo,
  });

  useEffect(() => {
    if (data) {
      setUsers(data);
    }
  }, [data, setUsers]);

  if (isLoading) return <Loader />;

  if (error) return <div>에러 발생: {error.message}</div>;

  const handleDownload = () => {
    alert('쿠폰을 다운로드합니다!');
  };

  const handleKeyPress = (value: string | number) => {
    if (value === 'delete') {
      setInputValue(inputValue.slice(0, -1)); // 마지막 한 글자 삭제
    } else {
      setInputValue(inputValue + value); // 숫자 추가
    }
  };

  return (
    <Layout className="mb-24">
      {users.map((user) => (
        <div key={user.id}>{user.name}</div>
      ))}
      <Stepper currentStep={1} steps={3} />
      <Stepper currentStep={2} steps={3} />
      <Button variant="primary" label="첫번째" />
      <Button variant="secondary" label="두번째" />
      <Button variant="text" label="텍스트" />
      <BackButton />
      <br />
      <Input name="first" />
      <Input name="second" className="w-1/2" />
      <Card
        variant="restaurant"
        title="멀티캠퍼스"
        category="한식"
        score={4.2}
        like={32}
      />
      <Card
        variant="payment"
        title="멀티캠퍼스"
        spend={12000}
        details={[
          {
            type: 'card',
            name: '꿈나무카드',
            amount: 9000,
          },
          {
            type: 'coupon',
            name: '쿠폰',
            amount: 4000,
          },
          {
            type: 'card',
            name: '신한4582',
            amount: 500,
          },
        ]}
      />
      <Card
        variant="donation"
        title="대우부대찌개"
        price={50000}
        date="24.07.20"
        location="서울시 강남구"
      />
      <hr className="mt-10" />
      <Card
        variant="review"
        title="브라운박사"
        score={4}
        content="우리 동네에 이런 맛집이 있다니! 우리 동네에 이런 맛집이 있다니! 우리 동네에 이런 맛집이 있다니! 우리 동네에 이런 맛집이 있다니!"
      />
      <Card
        variant="review"
        title="브라운박사"
        score={4}
        content="우리 동네에 이런 맛집이 있다니!"
      />
      <div className="p-4">
        <PageTitle title="제목컴포넌트" />
      </div>
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
        cardNumber="999 999999 999 99"
        cardOwner="박국민"
      />
      <Coupon
        couponID={1}
        storeName="대우부대찌개"
        cost={2000}
        onClick={handleDownload}
      />
      <div>
        <div>password variant</div>
        <KeyPad variant="password" onKeyPress={handleKeyPress} />
        <div>money variant</div>
        <KeyPad variant="money" onKeyPress={handleKeyPress} />
      </div>
    </Layout>
  );
};

export default MainPage;
