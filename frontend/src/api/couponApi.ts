import Axios from '.';

const prefix = '/donations/coupons';

// 가맹점 쿠폰 조회
export const getMyStoreCoupon = async (
  franchiseId: number,
): Promise<coupon> => {
  const response = await Axios(`${prefix}/${franchiseId}`);
  return response.data.data;
};

// 사용자 쿠폰 조회
export const getMyCoupon = async (franchiseId?: number) => {
  const url = franchiseId
    ? `${prefix}/my?franchiseId=${franchiseId}`
    : `${prefix}/my`;
  const response = await Axios(url);
  return response.data.data;
};

// 쿠폰 획득
export const acquireCoupon = async (
  couponUnit: number,
  franchiseId: number,
) => {
  const response = Axios.post(`${prefix}/my`, {
    couponUnit: couponUnit,
    franchiseId: franchiseId,
  });
  return response;
};
