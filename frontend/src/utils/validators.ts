// UserSignUp1

export const passwordRegex =
  /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/;

export const phoneNumberRegex = /^(\d{3})(\d{3,4})(\d{4})$/;

export const numericRegex = /^\d*$/;

export const certificateNumberRegex = /^\d{6}$/;

export const isValidPassword = (password: string) => {
  return passwordRegex.test(password);
};

export const isValidPhoneNumber = (phoneNumber: string) => {
  const cleaned = phoneNumber.replace(/-/g, '');
  return cleaned.length === 11;
};

export const isValidCertificateNumber = (certificate: string) => {
  return certificateNumberRegex.test(certificate);
};

export const formatPhoneNumber = (value: string) => {
  const cleaned = value.replace(/\D/g, '');
  const match = cleaned.match(phoneNumberRegex);
  if (match) {
    return `${match[1]}-${match[2]}-${match[3]}`;
  }
  return value;
};

// UserSignUp2

import * as Yup from 'yup';
export const NAME_REGEX = /^[ㄱ-힣a-zA-Z\s]+$/;
export const BIRTHDATE_FORMAT_REGEX = /^\d{4}\.\d{2}\.\d{2}$/;

export const validateName = (
  name: string,
): { isValid: boolean; error: string } => {
  if (name === '') {
    return { isValid: true, error: '' };
  }

  const isValidName = NAME_REGEX.test(name);
  if (!isValidName) {
    return { isValid: false, error: '이름은 한글과 영문만 입력 가능합니다.' };
  }

  return { isValid: true, error: '' };
};

export const validationSchema = Yup.object().shape({
  birthdate: Yup.string()
    .matches(BIRTHDATE_FORMAT_REGEX, '생년월일은 YYYY.MM.DD 형식이어야 합니다.')
    .test('isValidDate', '유효한 날짜를 입력해 주세요.', (value) => {
      if (!value) return false;
      const cleaned = value.replace(/\D/g, '');
      const year = parseInt(cleaned.slice(0, 4), 10);
      const month = parseInt(cleaned.slice(4, 6), 10);
      const day = parseInt(cleaned.slice(6, 8), 10);
      return (
        month >= 1 &&
        month <= 12 &&
        day >= 1 &&
        day <= 31 &&
        year <= new Date().getFullYear()
      );
    })
    .required('생년월일을 입력해 주세요.'),
});

export const formatBirthdate = (value: string): string => {
  const cleaned = value.replace(/\D/g, '');
  if (cleaned.length === 8) {
    return `${cleaned.slice(0, 4)}.${cleaned.slice(4, 6)}.${cleaned.slice(6, 8)}`;
  }
  return value;
};

// UserSignUp3

export const isNumeric = (value: string): boolean => /^\d*$/.test(value);

export const isSixDigits = (value: string): boolean => value.length === 6;

export const isPasswordMatch = (
  password: string,
  confirmPassword: string,
): boolean =>
  password === confirmPassword &&
  isSixDigits(password) &&
  isSixDigits(confirmPassword);

// UserSignUp4

export const certificateRegistrationNumberRegex = /^\d{10}$/;

export const seoulRegex = /^02-[0-9]{3,4}-[0-9]{4}$/;

export const otherRegionsRegex = /^[0]{1}[3-6]{1}[0-9]{1}-[0-9]{3,4}-[0-9]{4}$/;

export const formatTelephoneNumber = (phoneNumber: string) => {
  if (phoneNumber.startsWith('02')) {
    if (phoneNumber.length > 10) {
      return phoneNumber.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
    } else {
      return phoneNumber.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
    }
  } else {
    return phoneNumber.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
  }
};

// Donate1
export const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
