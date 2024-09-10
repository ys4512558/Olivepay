import Axios from '.';

const prefix = '/api/donations/coupons';

// 가맹점 쿠폰 조회
export const getMyStoreCoupon = async (
  franchiseId: number,
): Promise<coupon> => {
  const response = await Axios(`${prefix}/${franchiseId}`);
  return response.data;
};
