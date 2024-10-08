import Axios from './index';

const prefix = '/payments';

// 결제
export const pay = async (payData: payInfo) => {
  const response = await Axios.post(`${prefix}/pay`, payData);
  console.log(response);
  return response.data;
};

// 가맹점 결제 내역 조회
export const getFranchiseIncome = async (
  franchiseId: number,
  index?: number,
) => {
  const url = index
    ? `${prefix}/history/${franchiseId}?index=${index}`
    : `${prefix}/history/${franchiseId} `;
  const response = await Axios(url);
  return response.data.data;
};

// 유저 결제 내역 조회
export const getMyPaymentHistory = async (index?: number) => {
  const url = index
    ? `${prefix}/history/user?index=${index}`
    : `${prefix}/history/user`;
  const response = await Axios(url);
  console.log(response);
  return response.data.data;
};
