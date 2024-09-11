import { ChangeEvent, useRef, useState, useEffect } from 'react';
import { Input, Button } from '../common';

const UserSignUp1: React.FC<UserSignUpProps> = ({
  setStep,
  handleFormDataChange,
  formData,
}) => {
  const [userPwCheck, setUserPwCheck] = useState('');
  const [certificateNumber, setCertificateNumber] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [phoneNumberError, setPhoneNumberError] = useState('');
  const [certificateNumberError, setCertificateNumberError] = useState('');

  const phoneNumberRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (phoneNumberRef.current) {
      phoneNumberRef.current.focus();
    }
  }, []);

  const passwordRegex =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/;

  const formatPhoneNumber = (value: string) => {
    const cleaned = value.replace(/\D/g, '');
    const match = cleaned.match(/^(\d{3})(\d{3,4})(\d{4})$/);
    if (match) {
      return `${match[1]}-${match[2]}-${match[3]}`;
    }
    return value;
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    if (name === 'userPw') {
      setPasswordError('');
    }
    if (name === 'phoneNumber') {
      setPhoneNumberError('');
      const formattedPhone = formatPhoneNumber(value);
      handleFormDataChange(name, formattedPhone);
    } else {
      handleFormDataChange(name, value);
    }
  };

  const handleCertificateNumberChange = (e: ChangeEvent<HTMLInputElement>) => {
    setCertificateNumberError('');
    setCertificateNumber(e.target.value);
  };

  const handleUserPwCheckChange = (e: ChangeEvent<HTMLInputElement>) => {
    setUserPwCheck(e.target.value);
  };

  const isValidPassword = (password: string) => {
    return passwordRegex.test(password);
  };

  const isValidPhoneNumber = (phoneNumber: string) => {
    const cleaned = phoneNumber.replace(/-/g, '');
    return cleaned.length === 11;
  };

  const isValidCertificateNumber = (certificate: string) => {
    return certificate.length === 6;
  };

  const goToNextStep = (e: React.FormEvent) => {
    e.preventDefault();
    if (!isValidPhoneNumber(formData.phoneNumber)) {
      setPhoneNumberError('휴대폰 번호는 11자리여야 합니다.');
      return;
    }
    if (!isValidPassword(formData.userPw)) {
      setPasswordError(
        '비밀번호는 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.',
      );
      return;
    }
    if (formData.userPw !== userPwCheck) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
    if (!isValidCertificateNumber(certificateNumber)) {
      setCertificateNumberError('인증번호는 6자리여야 합니다.');
      return;
    }
    setStep(2);
  };

  return (
    <main>
      <form onSubmit={goToNextStep}>
        <article className="flex flex-col gap-y-6 p-5">
          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">휴대폰 번호</p>
            <div className="grid grid-cols-12 items-center gap-3">
              <Input
                ref={phoneNumberRef}
                name="phoneNumber"
                value={formData.phoneNumber}
                className="col-span-9 border border-gray-300 px-4"
                onChange={handleChange}
                required
              />
              <Button
                label="인증번호"
                variant="secondary"
                className="col-span-3 w-full text-xs font-bold"
              />
            </div>
            {phoneNumberError && (
              <p className="p-3 text-sm text-red-500">{phoneNumberError}</p>
            )}
          </figure>

          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">인증번호</p>
            <div className="grid grid-cols-12 items-center gap-3">
              <Input
                name="certificateNumber"
                value={certificateNumber}
                className="col-span-8 border border-gray-300 px-4"
                onChange={handleCertificateNumberChange}
                maxLength={6}
                required
              />
              <Button
                label="확인"
                variant="secondary"
                className="col-span-4 w-full text-xs font-bold"
              />
            </div>
            {certificateNumberError && (
              <p className="p-3 text-sm text-red-500">
                {certificateNumberError}
              </p>
            )}
          </figure>

          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">비밀번호</p>
            <Input
              name="userPw"
              type="password"
              value={formData.userPw}
              className="border border-gray-300 px-4"
              onChange={handleChange}
              required
            />
            {passwordError && (
              <p className="p-3 text-sm text-red-500">{passwordError}</p>
            )}
          </figure>

          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">비밀번호 확인</p>
            <Input
              name="userPwCheck"
              type="password"
              value={userPwCheck}
              className="border border-gray-300 px-4"
              onChange={handleUserPwCheckChange}
              required
            />
          </figure>

          <div className="py-10">
            <Button
              label="다음으로"
              variant="primary"
              className="w-full"
              type="submit"
            />
          </div>
        </article>
      </form>
    </main>
  );
};

export default UserSignUp1;
