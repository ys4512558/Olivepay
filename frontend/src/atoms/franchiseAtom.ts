import { atom } from 'jotai';

import { franchiseCategory, franchise, restaurants } from '../types/franchise';

export const franchiseAtom = atom<franchise>({
  franchiseId: 0,
  franchiseName: '',
  category: franchiseCategory.KOREAN,
  likes: 0,
  address: '',
  coupon2: 0,
  coupon4: 0,
  reviews: 0,
});

export const franchiseListAtom = atom<restaurants>([]);

export const franchiseDetailAtom = atom<franchise | null>(null);

export const franchiseIncomeAtom = atom<paymentList>([
  {
    paymentId: 1,
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
    paymentId: 2,
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
    paymentId: 5,
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
