import Axios from './index';

const prefix = '/api/auths';

// 일반 유저 로그인
export const userLogin = async (phoneNumber: string, password: string) => {
  return Axios.post(`${prefix}/users/login`, {
    phoneNumber,
    password,
  });
};

// 가맹점주 로그인
export const franchiserLogin = async (
  phoneNumber: string,
  password: string,
) => {
  return Axios.post(`${prefix}/owners/login`, {
    phoneNumber,
    password,
  });
};
