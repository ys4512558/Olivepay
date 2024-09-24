import { atom } from 'jotai';

import { franchiseCategory, franchise, restaurants } from '../types/franchise';

export const franchiseAtom = atom<franchise>({
  franchiseId: 4,
  franchiseName: '대우부대찌개',
  category: franchiseCategory.KOREAN,
  likes: 24,
  address: '서울시 강남구 역삼동',
  coupon2: 0,
  coupon4: 0,
  reviews: 234,
});

export const franchiseListAtom = atom<restaurants>([
  {
    franchiseId: 1,
    franchiseName: '온심옥',
    category: '한식',
    likes: 24,
    avgStars: 4.2,
    coupons: 21,
    latitude: 37.5025615786556,
    longitude: 127.034968504953,
  },
  {
    franchiseId: 2,
    franchiseName: '대우부대찌개',
    category: '한식',
    likes: 32,
    avgStars: 4.1,
    coupons: 0,
    latitude: 37.502696655575825,
    longitude: 127.03521737405278,
  },
  {
    franchiseId: 3,
    franchiseName: '후라토식당',
    category: '일식',
    likes: 322,
    avgStars: 4.82,
    coupons: 10,
    latitude: 37.5000238801213,
    longitude: 127.036508733456,
  },
  {
    franchiseId: 4,
    franchiseName: '선덕칼국수',
    category: '한식',
    likes: 122,
    avgStars: 4.92,
    coupons: 20,
    latitude: 37.503254660251,
    longitude: 127.037235242465,
  },
  {
    franchiseId: 5,
    franchiseName: '역삼정',
    category: '한식',
    likes: 1322,
    avgStars: 3.82,
    coupons: 10,
    latitude: 37.4972754978495,
    longitude: 127.037508202164,
  },
  {
    franchiseId: 6,
    franchiseName: '이도곰탕',
    category: '한식',
    likes: 322,
    avgStars: 4.82,
    coupons: 10,
    latitude: 37.5033732001773,
    longitude: 127.038461247911,
  },
  {
    franchiseId: 7,
    franchiseName: '신동궁감자탕',
    category: '일식',
    likes: 322,
    avgStars: 4.82,
    coupons: 10,
    latitude: 37.49788594917416,
    longitude: 127.03290815160956,
  },
]);

export const franchiseDetailAtom = atom<franchise | null>({
  franchiseId: 4,
  franchiseName: '대우부대찌개',
  category: franchiseCategory.KOREAN,
  likes: 24,
  isLiked: true,
  address: '서울시 강남구 역삼동',
  coupon2: 2,
  coupon4: 4,
});

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
