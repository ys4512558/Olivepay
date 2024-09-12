import Axios from './index';

const prefix = '/api/members/users';

// 유저 정보 조회
export const getUsersInfo = async () => {
  const response = await Axios(`${prefix}`);
  return response.data;
};

// 유저 비밀번호 검증
export const checkPassword = async (password: string) => {
  const response = await Axios.post(`${prefix}/password-check`, {
    password: password,
  });
  return response.data;
};

// 유저 정보 수정
export const patchNickname = async (nickname: string) => {
  const response = await Axios.patch(`${prefix}`, { nickname: nickname });
  return response.data;
};

// 유저 비밀번호 수정
export const patchPassword = async (password: string) => {
  const response = await Axios.patch(`${prefix}/password-change`, {
    password: password,
  });
  return response.data;
};
