import Axios from '.';
import { DonateInfo } from '../types/donate';

const prefix = '/donations';

// 후원하기
export const toDonate = async (data: DonateInfo) => {
  try {
    const response = await Axios.post(`${prefix}`, data);
    return response.data;
  } catch (error) {
    console.error('Error while making donation:', error);
    throw error;
  }
};

// 나의 후원 목록 조회하기
export const getMyDonations = async (email: string, phoneNumber: string) => {
  const response = await Axios.post(`${prefix}/donors/my`, {
    email: email,
    phoneNumber: phoneNumber,
  });
  return response.data;
};

// 후원 정보 조회하기
export const getDonationInfo = async () => {
  const response = await Axios(`${prefix}/donors`);
  return response.data;
};

// 공용기부금 사용내역 조회
export const getFundingUsages = async () => {
  const response = await Axios(`/fundings/usage`);
  return response.data;
};
