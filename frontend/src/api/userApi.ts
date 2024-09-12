import Axios from './index';

const prefix = '/api/members/users';

// 유저 정보 조회
export const getUsersInfo = async () => {
  const response = await Axios(`${prefix}`);
  return response.data;
};

// 유저 정보 수정
export const patchNickname = async (nickname: string) => {
  const response = await Axios.patch(`${prefix}`, { nickname: nickname });
  return response.data;
};
