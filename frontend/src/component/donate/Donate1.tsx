import { useState } from 'react';
import { Button, Input } from '../../component/common';
import { CommonProps } from '../../types/donate';

import {
  numericRegex,
  formatPhoneNumber,
  emailRegex,
  isValidPhoneNumber,
} from '../../utils/validators';

const Donate1: React.FC<CommonProps> = ({
  onNext,
  donateInfo,
  setDonateInfo,
}) => {
  const { phoneNumber, email } = donateInfo;
  const [phoneNumberError, setPhoneNumberError] = useState('');
  const [emailError, setEmailError] = useState('');
  const [formattedPhoneNumber, setFormattedPhoneNumber] = useState(phoneNumber);

  const handlePhoneNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputClean = e.target.value.replace(/-/g, '');
    if (!numericRegex.test(inputClean)) {
      setPhoneNumberError('숫자만 입력 가능합니다.');
    } else {
      setPhoneNumberError('');
      setDonateInfo((prevInfo) => ({
        ...prevInfo,
        phoneNumber: inputClean,
      }));
      setFormattedPhoneNumber(formatPhoneNumber(inputClean));
    }
  };

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDonateInfo((prevInfo) => ({
      ...prevInfo,
      email: e.target.value,
    }));
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
        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">휴대폰번호</p>
          <Input
            name="PhoneNumber"
            className="border border-gray-300 px-4"
            value={formattedPhoneNumber}
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

        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">이메일</p>
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
