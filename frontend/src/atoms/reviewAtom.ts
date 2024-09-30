import { atom } from 'jotai';

export const reviewAtom = atom<review[]>([]);

export const unwriteReviewAtom = atom<unwriteReview[]>([]);

export const franchiseReviewAtom = atom<review[]>([
  {
    reviewId: 1,
    memberId: 12,
    memberName: '김일태',
    stars: 2,
    content: '존맛탱구리',
  },
  {
    reviewId: 2,
    memberId: 12,
    memberName: '김일태',
    stars: 4,
    content: '존맛탱구리',
  },
]);
