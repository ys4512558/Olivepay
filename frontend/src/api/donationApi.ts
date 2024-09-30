import Axios from '.';

const prefix = '/api/donations';

// 후원하기
export const toDonate = async (
  email: string,
  phoneNumber: string,
  money: number,
  franchiseId: string,
  message: string,
  coupon4: number,
  coupon2: number,
  accountNumber: string,
) => {
  try {
    const response = await Axios.post(`${prefix}`, {
      email: email,
      phoneNumber: phoneNumber,
      money: money,
      franchiseId: franchiseId,
      message: message,
      coupon4: coupon4,
      coupon2: coupon2,
      accountNumber: accountNumber,
    });
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
