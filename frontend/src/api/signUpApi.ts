import Axios from './index';

const prefix = '/members';

// 일반 유저 회원가입
export const userSignUp = async (data: UserSignUpProps['formData1']) => {
  const response = await Axios.post(`${prefix}/users/sign-up`, data);
  return response.data;
};

// 가맹점주 회원가입
export const franchiserSignUp = async (data: UserSignUpProps['formData2']) => {
  const response = await Axios.post(`${prefix}/owners/sign-up`, data);
  return response.data;
};
