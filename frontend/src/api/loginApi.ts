import Axios from './index';
import Cookies from 'js-cookie';

const prefix = '/auths';

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
  return response.data;
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
  const { accessToken, role, franchiseId } = response.data.data;
  localStorage.setItem('accessToken', accessToken);
  localStorage.setItem('role', role);
  localStorage.setItem('franchiseId', franchiseId);
  Cookies.set('refreshToken', response.data.data.refreshToken);
  return response.data;
};

// 로그아웃
export const logout = async () => {
  const response = await Axios.post(`${prefix}/logout`);
  return response.data;
};
