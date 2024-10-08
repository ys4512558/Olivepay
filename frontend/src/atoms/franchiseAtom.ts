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

export const franchiseIncomeAtom = atom<paymentList>([]);

export const franchiseCouponAtom = atom<coupon>({
  coupon2: 12,
  coupon4: 4,
});
