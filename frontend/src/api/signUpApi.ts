import Axios from './index';

const prefix = '/members';

// 일반 유저 회원가입
export const userSignUp = async (
  name: string,
  password: string,
  phoneNumber: string,
  nickname: string,
  birthdate: string,
  pin: string,
) => {
  const response = await Axios.post(`${prefix}/users/sign-up`, {
    name: name,
    password: password,
    phoneNumber: phoneNumber,
    nickname: nickname,
    birthdate: birthdate,
    pin: pin,
  });
  return response.data;
};

// 가맹점주 회원가입
export const franchiserSignUp = async (
  name: string,
  password: string,
  phoneNumber: string,
  registrationNumber: string,
  franchiseName: string,
  category: string,
  telephoneNumber: string,
  address: string,
  latitude: number,
  longitude: number,
  rrnPrefix: string,
  rrnCheckDigit: string,
) => {
  const response = await Axios.post(`${prefix}/owners/sign-up`, {
    name: name,
    password: password,
    phoneNumber: phoneNumber,
    registrationNumber: registrationNumber,
    franchiseName: franchiseName,
    category: category,
    telephoneNumber: telephoneNumber,
    address: address,
    latitude: latitude,
    longitude: longitude,
    rrnPrefix: rrnPrefix,
    rrnCheckDigit: rrnCheckDigit,
  });
  return response.data;
};
