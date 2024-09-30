import { useState } from 'react';

import { Input, Button } from '../common';

import PasswordCheck from './PasswordCheck';

import { isValidPassword } from '../../utils/validators';

import { patchPassword, checkPassword } from '../../api/userApi';
import { useSnackbar } from 'notistack';

const PasswordChange: React.FC<infoChangeProps> = ({ closeModal }) => {
  const { enqueueSnackbar } = useSnackbar();
  const [step, setStep] = useState<number>(1);
  const [password, setPassword] = useState<string>('');
  const [newPassword, setNewPassword] = useState<string>('');
  const [newPasswordCheck, setNewPasswordCheck] = useState<string>('');
  const [passwordError, setPasswordError] = useState<string | null>(null);
  const [isPasswordValid, setIsPasswordValid] = useState<boolean>(false);
  const [isPasswordMatched, setIsPasswordMatched] = useState<boolean>(false);

  const handleStep = () => {
    checkPassword(password)
      .then(() => setStep(2))
      .catch(() => {
        enqueueSnackbar('비밀번호가 일치하지 않습니다.', {
          variant: 'error',
        });
        setPassword('');
      });
  };

  const handleChange = () => {
    patchPassword(newPassword).then(() => {
      closeModal();
      enqueueSnackbar('비밀번호 변경이 완료되었습니다.', {
        variant: 'success',
      });
    });
  };

  const handlePasswordCheck = () => {
    if (!isValidPassword(newPassword)) {
      setPasswordError('비밀번호 규칙을 다시 확인해주세요.');
      setIsPasswordValid(false);
    } else if (newPassword !== newPasswordCheck) {
      setPasswordError('비밀번호가 일치하지 않습니다.');
      setIsPasswordMatched(false);
    } else {
      setPasswordError(null);
      setIsPasswordValid(true);
      setIsPasswordMatched(true);
    }
  };

  const handleInputReg = () => {
    if (!isValidPassword(newPassword)) {
      setPasswordError('비밀번호 규칙을 다시 확인해주세요.');
      setIsPasswordValid(false);
    } else {
      setPasswordError(null);
      setIsPasswordValid(true);
    }
  };

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    setter: React.Dispatch<React.SetStateAction<string>>,
  ) => {
    setter(e.target.value);

    if (passwordError) {
      setPasswordError(null);
    }
  };

  return (
    <>
      {step === 1 ? (
        <PasswordCheck
          label="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          onClick={handleStep}
        />
      ) : (
        <div className="mt-8 flex flex-col gap-6 text-base">
          <p className="break-keep">
            📌 비밀번호는
            <span className="mx-1 text-lg font-semibold">
              8-16자리이며 영어 대문자, 영어 소문자, 숫자, 특수 문자
            </span>
            가 필수입니다.
          </p>
          <label className="ml-2">새 비밀번호</label>
          <form className="flex flex-col gap-4">
            <Input
              type="password"
              name="newPassword"
              value={newPassword}
              onChange={(e) => handleInputChange(e, setNewPassword)}
              onBlur={handleInputReg}
              maxLength={16}
              minLength={8}
            />
            <label className="ml-2">비밀번호 확인</label>
            <Input
              type="password"
              value={newPasswordCheck}
              onChange={(e) => handleInputChange(e, setNewPasswordCheck)}
              onBlur={handlePasswordCheck}
              maxLength={16}
              minLength={8}
            />
          </form>
          <div className="h-2 text-center">
            {passwordError && (
              <p className="animate-shake text-sm text-red-500">
                {passwordError}
              </p>
            )}
          </div>
          <div className="flex gap-2">
            <Button label="취소" className="bg-gray-500" onClick={closeModal} />
            <Button
              className="duration-500 ease-in-out"
              label="변경"
              onClick={handleChange}
              disabled={!isPasswordValid || !isPasswordMatched}
            />
          </div>
        </div>
      )}
    </>
  );
};

export default PasswordChange;
