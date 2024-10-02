import Axios from './index';
import Cookies from 'js-cookie';

const prefix = '/api/auths';

// 일반 유저 로그인
export const userLogin = async (phoneNumber: string, password: string) => {
  const response = await Axios.post(`${prefix}/users/login`, {
    phoneNumber,
    password,
  });
  const { accessToken, role } = response.data.data;
  localStorage.setItem('accessToken', accessToken);
  localStorage.setItem('role', role);
  Cookies.set('refreshToken', response.data.data.refreshToken);
  return response;
};

// 가맹점주 로그인
export const franchiserLogin = async (
  phoneNumber: string,
  password: string,
) => {
  const response = await Axios.post(`${prefix}/owners/login`, {
    phoneNumber,
    password,
  });
  const { accessToken, role } = response.data.data;
  localStorage.setItem('accessToken', accessToken);
  localStorage.setItem('role', role);
  Cookies.set('refreshToken', response.data.data.refreshToken);
  return response;
};
