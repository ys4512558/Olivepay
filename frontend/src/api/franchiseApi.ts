import Axios from './index';

const prefix = '/api/franchises';

// QR 코드 생성
export const makeQr = async (franchiseId: number, amount: string) => {
  const response = await Axios(
    `${prefix}/qr?franchiseId=${franchiseId}&amount=${amount}`,
  );
  return response.data;
};

// 스토어 상세 정보 조회
export const getStoreInfo = async (franchiseId: number) => {
  const response = await Axios(`${prefix}/${franchiseId}`);
  return response.data;
};
