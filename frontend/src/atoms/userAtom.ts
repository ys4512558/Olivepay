import { atom } from 'jotai';
import { BookmarkedFranchises } from '../types/franchise';

export const userAtom = atom<user>({
  memberId: 0,
  name: '',
  phoneNumber: '',
  nickname: '',
  role: 'TEMP_USER',
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

export const paymentHistoryAtom = atom<paymentList>([]);

export const bookmarkedFranchiseAtom = atom<BookmarkedFranchises>([]);

export const canPayAtom = atom<boolean>(true);
