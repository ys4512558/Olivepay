import { atom } from 'jotai';
import { Donate } from '../types/donate';

export const donateAtom = atom<Donate>([
  {
    franchiseId: 1,
    name: '가맹점1',
    address: '수원시 팔달구',
    money: 50000,
    date: '24-07-01',
  },
  {
    franchiseId: 2,
    name: '가맹점2',
    address: '수원시 장안구',
    money: 15000,
    date: '24-09-25',
  },
  {
    franchiseId: 5,
    name: '가맹점5',
    address: '수원시 권선구',
    money: 125000,
    date: '24-09-15',
  },
]);
