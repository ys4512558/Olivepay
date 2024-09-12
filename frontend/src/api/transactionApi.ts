import Axios from './index';

const prefix = '/api/transactions';

// 가맹점 결제 내역 조회
export const getFranchiseIncome = async (
  franchiseId: number,
  index: number,
) => {
  const response = await Axios(
    `${prefix}/franchise/${franchiseId}?index=${index}`,
  );
  return response.data;
};
