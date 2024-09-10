import { atom } from 'jotai';

import { franchiseCategory, franchise } from '../types/franchise';

export const franchiseAtom = atom<franchise>({
  franchiseId: 0,
  franchiseName: '',
  category: franchiseCategory.KOREAN,
  likes: 0,
  isLiked: false,
  address: '',
  coupon2: 0,
  coupon4: 0,
  reviews: [],
});
