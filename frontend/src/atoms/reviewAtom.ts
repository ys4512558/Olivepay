import { atom } from 'jotai';

export const reviewAtom = atom<review[]>([
  {
    reviewId: 1,
    franchise: {
      id: 1,
      name: '김밥천국',
    },
    stars: 2,
    content: '존맛탱구리',
  },
  {
    reviewId: 2,
    franchise: {
      id: 2,
      name: '김밥지옥',
    },
    stars: 4,
    content: '존맛탱구리',
  },
]);

export const unwriteReviewAtom = atom<unwriteReview[]>([
  {
    franchise: {
      id: 1,
      name: '김밥천국',
    },
    createdAt: '2024-09-04 15:08:01',
  },
  {
    franchise: {
      id: 2,
      name: '김밥싸피',
    },
    createdAt: '2024-09-05 17:08:01',
  },
]);
