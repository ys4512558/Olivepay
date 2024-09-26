import { useState } from 'react';
import { Button, Input } from '../common';
import { DonateProps } from '../../types/donate';
import {
  numericRegex,
  isValidPhoneNumber,
  formatPhoneNumber,
  emailRegex,
} from '../../utils/validators';

const GetDonationList: React.FC<DonateProps> = ({
  onNext,
  email,
  setEmail,
  phoneNumber,
  setPhoneNumber,
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
        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">전화번호</p>
          <Input
            value={phoneNumber}
            onChange={handlePhoneNumberChange}
            className="border border-gray-300 px-4"
            container="col-span-9"
            required
            maxLength={13}
            name="phoneNumber"
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
            value={email}
            onChange={handleEmailChange}
            className="border border-gray-300 px-4"
            container="col-span-9"
            required
            maxLength={30}
            name="email"
          />
          {emailError && (
            <p className="break-keep p-3 text-sm text-red-500">{emailError}</p>
          )}
        </figure>

        <Button
          label="조회하기"
          className="my-10"
          variant="primary"
          onClick={handleNext}
        />
      </div>
    </main>
  );
};

export default GetDonationList;
