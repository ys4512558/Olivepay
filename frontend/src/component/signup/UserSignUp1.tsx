import { ChangeEvent, useRef, useState, useEffect } from 'react';
import { Input, Button, Layout } from '../common';
import {
  isValidPassword,
  isValidPhoneNumber,
  isValidCertificateNumber,
  formatPhoneNumber,
  numericRegex,
} from '../../utils/validators';

const UserSignUp1: React.FC<UserSignUpProps> = ({
  signupType,
  setStep,
  handleFormDataChange,
  formData1,
  formData2,
}) => {
  const [userPwCheck, setUserPwCheck] = useState('');
  const [certificateNumber, setCertificateNumber] = useState('');
  const [passwordError, setPasswordError] = useState('');
  const [phoneNumberError, setPhoneNumberError] = useState('');
  const [certificateNumberError, setCertificateNumberError] = useState('');
  const [rrnPrefixError, setRrnPrefixError] = useState('');
  const [rrnCheckDigitError, setRrnCheckDigitError] = useState('');

  const phoneNumberRef = useRef<HTMLInputElement>(null);
  const nameRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (signupType === 'for_user' && phoneNumberRef.current) {
      phoneNumberRef.current.focus();
    } else if (signupType === 'for_franchiser' && nameRef.current) {
      nameRef.current.focus();
    }
  }, [signupType]);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    const formType = signupType === 'for_user' ? 'formData1' : 'formData2';

    if (
      name === 'phoneNumber' ||
      name === 'rrnPrefix' ||
      name === 'rrnCheckDigit'
    ) {
      const inputClean =
        name === 'phoneNumber' ? value.replace(/-/g, '') : value;
      if (!numericRegex.test(inputClean)) {
        if (name === 'phoneNumber') {
          setPhoneNumberError('숫자만 입력 가능합니다.');
        } else if (name === 'rrnPrefix') {
          setRrnPrefixError('숫자만 입력 가능합니다.');
        } else if (name === 'rrnCheckDigit') {
          setRrnCheckDigitError('숫자만 입력 가능합니다.');
        }
        return;
      } else {
        if (name === 'phoneNumber') {
          setPhoneNumberError('');
          const formattedPhone = formatPhoneNumber(value);
          handleFormDataChange(name, formattedPhone, formType);
        } else if (name === 'rrnPrefix' || name === 'rrnCheckDigit') {
          if (name === 'rrnPrefix') setRrnPrefixError('');
          if (name === 'rrnCheckDigit') setRrnCheckDigitError('');
          handleFormDataChange(name, value, formType);
        }
      }
    } else {
      handleFormDataChange(name, value, formType);
    }
  };

  const handleCertificateNumberChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    if (!numericRegex.test(value)) {
      setCertificateNumberError('숫자만 입력 가능합니다.');
      return;
    }
    setCertificateNumberError('');
    setCertificateNumber(value);
  };

  const handleUserPwCheckChange = (e: ChangeEvent<HTMLInputElement>) => {
    setUserPwCheck(e.target.value);
  };

  const goToNextStep = (e: React.FormEvent) => {
    e.preventDefault();
    const formType = signupType === 'for_user' ? formData1 : formData2;

    if (!isValidPhoneNumber(formType.phoneNumber)) {
      setPhoneNumberError('휴대폰 번호는 11자리여야 합니다.');
      return;
    }
    if (!isValidPassword(formType.userPw)) {
      setPasswordError(
        '비밀번호는 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.',
      );
      return;
    }
    if (formType.userPw !== userPwCheck) {
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
    <Layout>
      <main>
        <form onSubmit={goToNextStep}>
          <article className="flex flex-col gap-y-6 p-5">
            {signupType === 'for_franchiser' && (
              <figure className="flex flex-col gap-y-1">
                <p className="ms-3 text-gray-600">이름</p>
                <Input
                  className="col-span-9 border border-gray-300 px-4"
                  required
                  ref={nameRef}
                  maxLength={13}
                  name="name"
                  value={formData2.name}
                  onChange={handleChange}
                />
              </figure>
            )}

            {signupType === 'for_franchiser' && (
              <figure className="flex flex-col gap-y-2">
                <p className="ms-3 text-gray-600">주민등록번호</p>
                <div className="flex items-center gap-3">
                  <Input
                    className="w-52 border border-gray-300 px-4 py-2"
                    required
                    maxLength={6}
                    name="rrnPrefix"
                    value={formData2.rrnPrefix}
                    onChange={handleChange}
                    placeholder="앞자리 (6자리)"
                  />
                  <span>-</span>
                  <div className="flex items-center gap-1">
                    <Input
                      className="w-16 border border-gray-300 px-2 py-2"
                      required
                      maxLength={1}
                      name="rrnCheckDigit"
                      value={formData2.rrnCheckDigit}
                      onChange={handleChange}
                      placeholder="1"
                    />
                    <span className="text-lg text-gray-500">******</span>
                  </div>
                </div>
                <div className="flex items-center gap-3">
                  <div className="w-52">
                    {rrnPrefixError && (
                      <p className="break-keep p-3 text-sm text-red-500">
                        {rrnPrefixError}
                      </p>
                    )}
                  </div>
                  <div>
                    {rrnCheckDigitError && (
                      <p className="break-keep p-3 text-sm text-red-500">
                        {rrnCheckDigitError}
                      </p>
                    )}
                  </div>
                </div>
              </figure>
            )}

            <figure className="flex flex-col gap-y-1">
              <p className="ms-3 text-gray-600">휴대폰 번호</p>
              <div className="grid grid-cols-12 items-center gap-3">
                <Input
                  ref={phoneNumberRef}
                  name="phoneNumber"
                  value={
                    signupType === 'for_user'
                      ? formData1.phoneNumber
                      : formData2.phoneNumber
                  }
                  className="col-span-9 border border-gray-300 px-4"
                  onChange={handleChange}
                  required
                  maxLength={13}
                />
                <Button
                  label="인증번호"
                  variant="secondary"
                  className="col-span-3 w-full text-xs font-bold"
                />
              </div>
              {phoneNumberError && (
                <p className="break-keep p-3 text-sm text-red-500">
                  {phoneNumberError}
                </p>
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
                <p className="break-keep p-3 text-sm text-red-500">
                  {certificateNumberError}
                </p>
              )}
            </figure>

            <figure className="flex flex-col gap-y-1">
              <p className="ms-3 text-gray-600">비밀번호</p>
              <Input
              	container="col-span-9"
                name="userPw"
                type="password"
                value={
                  signupType === 'for_user'
                    ? formData1.userPw
                    : formData2.userPw
                }
                className="border border-gray-300 px-4"
                onChange={handleChange}
                required
              />
              {passwordError && (
                <p className="break-keep p-3 text-sm text-red-500">
                  {passwordError}
                </p>
              )}
            </figure>

            <figure className="flex flex-col gap-y-1">
              <p className="ms-3 text-gray-600">비밀번호 확인</p>
              <Input
              	container="col-span-8"
                name="userPwCheck"
                type="password"
                value={userPwCheck}
                className="border border-gray-300 px-4"
                onChange={handleUserPwCheckChange}
                required
              />
            </figure>

            {signupType === 'for_user' && (
              <div className="py-10">
                <Button label="다음으로" variant="primary" type="submit" />
              </div>
            )}

            {signupType === 'for_franchiser' && (
              <div className="py-10">
                <Button label="다음으로" variant="primary" type="submit" />
              </div>
            )}
          </article>
        </form>
      </main>
    </Layout>
  );
};

export default UserSignUp1;
