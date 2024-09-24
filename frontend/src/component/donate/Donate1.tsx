import { useState } from 'react';
import { Button, Input } from '../../component/common';
import { Donate1Props } from '../../types/donate';

import {
  numericRegex,
  formatPhoneNumber,
  emailRegex,
  isValidPhoneNumber,
} from '../../utils/validators';

const Donate1: React.FC<Donate1Props> = ({
  onNext,
  name,
  setName,
  phoneNumber,
  setPhoneNumber,
  email,
  setEmail,
}) => {
  const [phoneNumberError, setPhoneNumberError] = useState('');
  const [emailError, setEmailError] = useState('');

  const handlePhoneNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputClean = e.target.value.replace(/-/g, '');
    if (!numericRegex.test(inputClean)) {
      setPhoneNumberError('숫자만 입력 가능합니다.');
    } else {
      setPhoneNumberError('');
      const formattedPhone = formatPhoneNumber(inputClean);
      setPhoneNumber(formattedPhone);
    }
  };

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
    setEmailError('');
  };

  const handleNext = () => {
    if (!emailRegex.test(email)) {
      setEmailError('유효한 이메일 주소를 입력해주세요.');
      return;
    }
    if (!isValidPhoneNumber(phoneNumber)) {
      setPhoneNumberError('휴대폰 번호는 11자리여야 합니다.');
      return;
    }
    setEmailError('');
    setPhoneNumberError('');
    onNext();
  };

  return (
    <main className="p-10">
      <div className="flex flex-col gap-y-6">
        <figure className="flex flex-col gap-y-1">
          <p className="ms-3 font-semibold text-gray-600">후원하시는 분 성함</p>
          <Input
            name="DonatorName"
            className="border border-gray-300 px-4"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
            maxLength={20}
          />
        </figure>

        <figure className="flex flex-col gap-y-1">
          <p className="ms-3 font-semibold text-gray-600">전화번호</p>
          <Input
            name="PhoneNumber"
            className="border border-gray-300 px-4"
            value={phoneNumber}
            onChange={handlePhoneNumberChange}
            required
            maxLength={13}
          />
          {phoneNumberError && (
            <p className="break-keep p-3 text-sm text-red-500">
              {phoneNumberError}
            </p>
          )}
        </figure>

        <figure className="flex flex-col gap-y-1">
          <p className="ms-3 font-semibold text-gray-600">이메일</p>
          <Input
            name="Email"
            className="border border-gray-300 px-4"
            value={email}
            onChange={handleEmailChange}
            required
          />
          {emailError && (
            <p className="break-keep p-3 text-sm text-red-500">{emailError}</p>
          )}
        </figure>
        <div className="py-10">
          <Button label="다음으로" variant="primary" onClick={handleNext} />
        </div>
      </div>
    </main>
  );
};

export default Donate1;
