import { atom } from 'jotai';

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
