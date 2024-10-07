import { franchiseCategory } from '../types/franchise';
import Axios from './index';

const prefix = '/franchises';

// QR 코드 생성
export const makeQr = async (franchiseId: number, amount: string) => {
  const response = await Axios(
    `${prefix}/qr?franchiseId=${franchiseId}&amount=${amount}`,
  );
  return response.data.data;
};

// 스토어 상세 정보 조회
export const getStoreInfo = async (franchiseId: number) => {
  const response = await Axios(`${prefix}/${franchiseId}`);
  return response.data;
};

// 조건에 맞는 가맹점 검색
export const getFranchises = async (
  latitude: number,
  longitude: number,
  category?: keyof typeof franchiseCategory,
) => {
  const url = category
    ? `${prefix}?latitude=${latitude}&longitude=${longitude}&category=${category}`
    : `${prefix}?latitude=${latitude}&longitude=${longitude}`;
  const response = await Axios(url);
  return response.data.data;
};

// 가맹점 상세 조회
export const getFranchiseDetail = async (franchiseId: number) => {
  const response = await Axios(`${prefix}/${franchiseId}`);
  return response.data.data;
};

// 좋아요 토글
export const toggleLike = async (franchiseId: number) => {
  const response = await Axios.post(`${prefix}/likes/user/${franchiseId}`);
  return response.data;
};

// 좋아하는 가맹점 조회
export const getFavoriteFranchises = async () => {
  const response = await Axios(`${prefix}/likes/user`);
  return response.data.data;
};
