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

  const phoneNumberRef = useRef<HTMLInputElement>(null); // 휴대폰 번호 입력 필드에 포커스를 주기 위한 useRef

  useEffect(() => {
    if (phoneNumberRef.current) {
      phoneNumberRef.current.focus(); // 컴포넌트가 렌더링되면 첫 번째 Input에 포커스
    }
  }, []);

  const passwordRegex =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/;

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    if (name === 'userPw') {
      setPasswordError('');
    }
    handleFormDataChange(name, value);
  };

  const handleCertificateNumberChange = (e: ChangeEvent<HTMLInputElement>) => {
    setCertificateNumber(e.target.value);
  };

  const handleUserPwCheckChange = (e: ChangeEvent<HTMLInputElement>) => {
    setUserPwCheck(e.target.value);
  };

  const isValidPassword = (password: string) => {
    return passwordRegex.test(password);
  };

  const goToNextStep = (e: React.FormEvent) => {
    e.preventDefault();
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
