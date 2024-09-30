import { atom } from 'jotai';
import { BookmarkedFranchises } from '../types/franchise';

export const userAtom = atom<user>({
  id: 12,
  name: '김일태',
  phoneNumber: '010-1234-5678',
  nickName: '일태',
});

export const creditCardAtom = atom<CreditCard>([
  {
    cardId: '1',
    realCardNumber: '1005726548291611',
    isDefault: true,
    cardCompany: '꿈나무',
  },
  {
    cardId: '2',
    realCardNumber: '1002726548291612',
    isDefault: false,
    cardCompany: '우리',
  },
]);

export const couponAtom = atom<myCoupon[]>([]);

export const paymentHistoryAtom = atom<paymentList>([
  {
    transactionId: 1,
    amount: 13500,
    createdAt: '2024년 9월 10일',
    franchise: {
      id: 1,
      name: '김밥천국',
    },
    details: [
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
    ],
  },
  {
    transactionId: 2,
    amount: 16000,
    createdAt: '2024년 9월 10일',
    franchise: {
      id: 1,
      name: '김밥천국',
    },
    details: [
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
        amount: 3000,
      },
    ],
  },
  {
    transactionId: 5,
    amount: 10500,
    createdAt: '2024년 9월 12일',
    franchise: {
      id: 1,
      name: '김밥천국',
    },
    details: [
      {
        type: 'card',
        name: '꿈나무카드',
        amount: 9000,
      },
      {
        type: 'coupon',
        name: '쿠폰',
        amount: 1500,
      },
    ],
  },
]);

export const bookmarkedFranchiseAtom = atom<BookmarkedFranchises>([]);
