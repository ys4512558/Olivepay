import { ChangeEvent, useRef, useState, useEffect } from 'react';
import { Input, Button } from '../common';
import {
  isValidPassword,
  isValidPhoneNumber,
  isValidCertificateNumber,
  formatPhoneNumber,
  numericRegex,
} from '../../utils/validators';
import { removePhoneFormatting } from '../../utils/formatter';
import {
  getCertificateNumber,
  checkCertificateNumber,
} from '../../api/signUpApi';
import { useSnackbar } from 'notistack';
import axios from 'axios';

const UserSignUp1: React.FC<UserSignUpProps> = ({
  signupType,
  setStep,
  handleFormDataChange,
  formData1,
  formData2,
}) => {
  const { enqueueSnackbar } = useSnackbar();
  const [userPwCheck, setUserPwCheck] = useState('');
  const [certificateNumber, setCertificateNumber] = useState('');
  const [infoMessage, setInfoMessage] = useState('');

  const [errorMessages, setErrorMessages] = useState({
    passwordError: '',
    phoneNumberError: '',
    certificateNumberError: '',
    rrnPrefixError: '',
    rrnCheckDigitError: '',
  });
  const [loading, setLoading] = useState(false);
  const [timer, setTimer] = useState(0);
  const [isVerified, setIsVerified] = useState(false);

  const phoneNumberRef = useRef<HTMLInputElement>(null);
  const nameRef = useRef<HTMLInputElement>(null);

  // 타이머 관리
  useEffect(() => {
    let interval: NodeJS.Timeout;
    if (timer > 0) {
      interval = setInterval(() => {
        setTimer((prevTimer) => prevTimer - 1);
      }, 1000);
    }
    return () => clearInterval(interval);
  }, [timer]);

  useEffect(() => {
    if (signupType === 'for_user' && phoneNumberRef.current) {
      phoneNumberRef.current.focus();
    } else if (signupType === 'for_franchiser' && nameRef.current) {
      nameRef.current.focus();
    }
  }, [signupType]);

  const updateErrorMessages = (field: string, message: string) => {
    setErrorMessages((prevErrors) => ({
      ...prevErrors,
      [field]: message,
    }));
  };

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
          updateErrorMessages('phoneNumberError', '숫자만 입력 가능합니다.');
        } else if (name === 'rrnPrefix') {
          updateErrorMessages('rrnPrefixError', '숫자만 입력 가능합니다.');
        } else if (name === 'rrnCheckDigit') {
          updateErrorMessages('rrnCheckDigitError', '숫자만 입력 가능합니다.');
        }
        return;
      } else {
        if (name === 'phoneNumber') {
          updateErrorMessages('phoneNumberError', '');
          const formattedPhone = formatPhoneNumber(value);
          handleFormDataChange(name, formattedPhone, formType);
        } else if (name === 'rrnPrefix' || name === 'rrnCheckDigit') {
          updateErrorMessages(
            name === 'rrnPrefix' ? 'rrnPrefixError' : 'rrnCheckDigitError',
            '',
          );
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
      updateErrorMessages('certificateNumberError', '숫자만 입력 가능합니다.');
      return;
    }
    updateErrorMessages('certificateNumberError', '');
    setInfoMessage('');
    setCertificateNumber(value);
  };

  const handleUserPwCheckChange = (e: ChangeEvent<HTMLInputElement>) => {
    setUserPwCheck(e.target.value);
  };

  const formatTime = (time: number) => {
    const minutes = Math.floor(time / 60);
    const seconds = time % 60;
    return `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
  };

  const handleCertificateNumberRequest = async () => {
    const phoneNumber =
      signupType === 'for_user' ? formData1.phoneNumber : formData2.phoneNumber;
    if (!isValidPhoneNumber(phoneNumber)) {
      updateErrorMessages('phoneNumberError', '올바른 전화번호를 입력하세요.');
      return;
    }
    const pN = removePhoneFormatting(phoneNumber);
    try {
      setLoading(true);
      await getCertificateNumber(pN);
      setTimer(10);
      setInfoMessage('인증번호 전송이 완료되었습니다.');
    } catch (error) {
      if (axios.isAxiosError(error)) {
        if (error.response && error.response.data) {
          updateErrorMessages('phoneNumberError', error.response.data.message);
        }
      }
    } finally {
      setLoading(false);
    }
  };

  const handleCertificateNumberCheck = async () => {
    const phoneNumber =
      signupType === 'for_user' ? formData1.phoneNumber : formData2.phoneNumber;
    const pN = removePhoneFormatting(phoneNumber);
    if (!isValidPhoneNumber(pN)) {
      updateErrorMessages(
        'phoneNumberError',
        '유효한 휴대폰 번호를 입력하세요.',
      );
      return;
    }
    if (!isValidCertificateNumber(certificateNumber)) {
      updateErrorMessages(
        'certificateNumberError',
        '인증번호는 6자리여야 합니다.',
      );
      return;
    }
    try {
      setLoading(true);
      const response = await checkCertificateNumber(pN, certificateNumber);
      enqueueSnackbar(`${response.message}`, {
        variant: 'success',
      });
      setIsVerified(true);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        if (error.response && error.response.data) {
          updateErrorMessages(
            'certificateNumberError',
            error.response.data.message,
          );
        }
      }
    } finally {
      setLoading(false);
    }
  };

  const goToNextStep = (e: React.FormEvent) => {
    e.preventDefault();
    const formType = signupType === 'for_user' ? formData1 : formData2;

    if (!isValidPhoneNumber(formType.phoneNumber)) {
      updateErrorMessages(
        'phoneNumberError',
        '휴대폰 번호는 11자리여야 합니다.',
      );
      return;
    }
    if (!isValidPassword(formType.password)) {
      updateErrorMessages(
        'passwordError',
        '비밀번호는 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.',
      );
      return;
    }
    if (formType.password !== userPwCheck) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
    if (!isValidCertificateNumber(certificateNumber)) {
      updateErrorMessages(
        'certificateNumberError',
        '인증번호는 6자리여야 합니다.',
      );
      return;
    }
    setStep(2);
  };

  return (
    <main>
      <form onSubmit={goToNextStep}>
        <article className="flex flex-col gap-y-6 p-5">
          {signupType === 'for_franchiser' && (
            <figure className="mb-4 flex flex-col gap-y-2">
              <p className="ms-3 text-md font-semibold text-gray-600">이름</p>
              <Input
                className="border border-gray-300 px-4"
                container="col-span-9"
                required
                ref={nameRef}
                maxLength={13}
                name="name"
                value={formData2.name}
                onChange={handleChange}
                placeholder="김싸피"
              />
            </figure>
          )}

          {signupType === 'for_franchiser' && (
            <figure className="flex flex-col gap-y-2">
              <p className="ms-3 text-md font-semibold text-gray-600">
                주민등록번호
              </p>
              <div className="flex items-center gap-3">
                <Input
                  className="w-52 border border-gray-300 px-4 py-2"
                  required
                  maxLength={6}
                  name="rrnPrefix"
                  value={formData2.rrnPrefix}
                  onChange={handleChange}
                  placeholder="970101"
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
                  <div className="min-h-5">
                    {errorMessages.rrnPrefixError && (
                      <p className="mt-1 ps-3 text-sm text-red-500">
                        {errorMessages.rrnPrefixError}
                      </p>
                    )}
                  </div>
                </div>
                <div className="min-h-5">
                  {errorMessages.rrnCheckDigitError && (
                    <p className="mt-1 text-sm text-red-500">
                      {errorMessages.rrnCheckDigitError}
                    </p>
                  )}
                </div>
              </div>
            </figure>
          )}

          <figure className="flex flex-col gap-y-2">
            <p className="ms-3 text-md font-semibold text-gray-600">
              휴대폰 번호
            </p>
            <div className="grid grid-cols-12 items-center gap-3">
              <Input
                ref={phoneNumberRef}
                name="phoneNumber"
                value={
                  signupType === 'for_user'
                    ? formData1.phoneNumber
                    : formData2.phoneNumber
                }
                className="border border-gray-300 px-4"
                container={isVerified ? 'col-span-12' : 'col-span-9'}
                onChange={handleChange}
                required
                maxLength={13}
                placeholder="숫자만 입력하세요"
              />
              {!isVerified && (
                <Button
                  label={timer > 0 ? formatTime(timer) : '인증번호'}
                  variant="secondary"
                  className="col-span-3 w-full text-xs font-bold"
                  type="button"
                  onClick={handleCertificateNumberRequest}
                  disabled={timer > 0}
                />
              )}
            </div>
            <div className="min-h-5">
              {errorMessages.phoneNumberError && (
                <p className="mt-1 ps-3 text-sm text-red-500">
                  {errorMessages.phoneNumberError}
                </p>
              )}
              {infoMessage && (
                <p className="mt-1 ps-3 text-sm text-green-500">
                  {infoMessage}
                </p>
              )}
            </div>
          </figure>

          <figure className="flex flex-col gap-y-2">
            <p className="ms-3 text-md font-semibold text-gray-600">인증번호</p>
            <div className="grid grid-cols-12 items-center gap-3">
              <Input
                container={isVerified ? 'col-span-12' : 'col-span-8'}
                name="certificateNumber"
                value={certificateNumber}
                className="border border-gray-300 px-4"
                onChange={handleCertificateNumberChange}
                maxLength={6}
                required
                placeholder="123456"
                readOnly={isVerified}
              />
              {!isVerified && (
                <Button
                  label="확인"
                  variant="secondary"
                  className="col-span-4 w-full text-xs font-bold"
                  onClick={handleCertificateNumberCheck}
                  disabled={loading}
                />
              )}
            </div>
            <div className="min-h-5">
              {errorMessages.certificateNumberError && (
                <p className="mt-1 ps-3 text-sm text-red-500">
                  {errorMessages.certificateNumberError}
                </p>
              )}
            </div>
          </figure>

          <figure className="flex flex-col gap-y-2">
            <p className="ms-3 text-md font-semibold text-gray-600">비밀번호</p>
            <Input
              container="col-span-9"
              name="password"
              type="password"
              value={
                signupType === 'for_user'
                  ? formData1.password
                  : formData2.password
              }
              className="border border-gray-300 px-4"
              onChange={handleChange}
              required
              placeholder="대소문자, 숫자, 특수문자가 모두 포함되어야 합니다."
            />
            <div className="min-h-5">
              {errorMessages.passwordError && (
                <p className="mt-1 ps-3 text-sm text-red-500">
                  {errorMessages.passwordError}
                </p>
              )}
            </div>
          </figure>

          <figure className="flex flex-col gap-y-2">
            <p className="ms-3 text-md font-semibold text-gray-600">
              비밀번호 확인
            </p>
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
            <div className="pb-20 pt-10">
              <Button label="다음으로" variant="primary" type="submit" />
            </div>
          )}

          {signupType === 'for_franchiser' && (
            <div className="pb-20 pt-10">
              <Button label="다음으로" variant="primary" type="submit" />
            </div>
          )}
        </article>
      </form>
    </main>
  );
};

export default UserSignUp1;
