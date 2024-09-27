import { atom } from 'jotai';

import { franchiseCategory, franchise, restaurants } from '../types/franchise';

export const franchiseAtom = atom<franchise>({
  franchiseId: 3,
  franchiseName: '대우부대찌개',
  category: franchiseCategory.KOREAN,
  likes: 24,
  address: '서울시 강남구 역삼동',
  coupon2: 0,
  coupon4: 0,
  reviews: 234,
});

export const franchiseListAtom = atom<restaurants>([]);

export const franchiseDetailAtom = atom<franchise | null>(null);

export const franchiseIncomeAtom = atom<paymentList>([
  {
    transactionId: 1,
    amount: 13500,
    createdAt: '2024년 9월 10일',
    details: [
      {
        type: 'card',
        name: '꿈나무카드',
        amount: 9000,
      },
      {
        type: 'coupon',
        name: '4000',
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
    details: [
      {
        type: 'card',
        name: '꿈나무카드',
        amount: 9000,
      },
      {
        type: 'coupon',
        name: '4000',
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
    details: [
      {
        type: 'card',
        name: '꿈나무카드',
        amount: 9000,
      },
      {
        type: 'coupon',
        name: '2000',
        amount: 1500,
      },
    ],
  },
]);

export const franchiseCouponAtom = atom<coupon>({
  coupon2: 12,
  coupon4: 4,
});
