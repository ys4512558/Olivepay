import { Input, Button, KeyPad } from '../common';
import { useState, useEffect } from 'react';
import { isNumeric, isPasswordMatch } from '../../helper/utils/validators';
import { isNumeric, isPasswordMatch } from '../../utils/validators';

const UserSignUp3: React.FC<UserSignUpProps> = ({
  formData,
  handleFormDataChange,
  handleSubmit,
}) => {
  const [activeField, setActiveField] = useState<
    'password' | 'confirmPassword' | null
  >(null);
  const [showKeyPad, setShowKeyPad] = useState(false);
  const [confirmPin, setConfirmPin] = useState('');
  const [passwordErrorMessage, setPasswordErrorMessage] = useState('');
  const [confirmPasswordErrorMessage, setConfirmPasswordErrorMessage] =
    useState('');

  const handleKeyboardChange =
    (field: string) => (e: React.ChangeEvent<HTMLInputElement>) => {
      const value = e.target.value.slice(0, 6);

      if (!isNumeric(value)) {
        if (field === 'password') {
          setPasswordErrorMessage('숫자만 입력 가능합니다.');
        }
        return;
      }

      if (field === 'password') {
        setPasswordErrorMessage('');
        handleFormDataChange('pin', value);
      } else if (field === 'confirmPassword') {
        setConfirmPin(value);
      }
    };

  const handleKeyPress = (value: string | number) => {
    if (activeField === 'password') {
      const newPassword =
        value === 'delete'
          ? formData.pin.slice(0, -1)
          : formData.pin.length < 6
            ? formData.pin + value
            : formData.pin;

      handleFormDataChange('pin', newPassword);
    } else if (activeField === 'confirmPassword') {
      const newConfirmPassword =
        value === 'delete'
          ? confirmPin.slice(0, -1)
          : confirmPin.length < 6
            ? confirmPin + value
            : confirmPin;

      setConfirmPin(newConfirmPassword);
    }
  };

  useEffect(() => {
    if (
      formData.pin.length === 6 &&
      confirmPin.length === 6 &&
      !isPasswordMatch(formData.pin, confirmPin)
    ) {
      setConfirmPasswordErrorMessage('비밀번호가 다릅니다.');
    } else {
      setConfirmPasswordErrorMessage('');
    }

    if (
      formData.pin.length < 6 ||
      confirmPin.length < 6 ||
      !isPasswordMatch(formData.pin, confirmPin)
    ) {
      setShowKeyPad(true);
    }
  }, [confirmPin, formData.pin]);

  if (isPasswordMatch(formData.pin, confirmPin) && showKeyPad) {
    setShowKeyPad(false);
  }

  return (
    <main>
      <article className="flex flex-col gap-y-6 p-10">
        <h3 className="py-10 text-center text-sm font-bold">
          간편 비밀번호 6자리를 입력해주세요
        </h3>

        <figure className="flex flex-col gap-y-1">
          <p className="ms-3 text-gray-600">간편 비밀번호</p>
          <Input
            name="password"
            type="password"
            className="w-full border border-gray-300 text-sm"
            onClick={() => {
              setActiveField('password');
              setShowKeyPad(true);
            }}
            onChange={handleKeyboardChange('password')}
            value={formData.pin}
            placeholder="비밀번호 6자리 입력"
            maxLength={6}
            required
          />{' '}
          {passwordErrorMessage && (
            <p className="break-keep p-3 text-sm text-red-500">
              {passwordErrorMessage}
            </p>
          )}
        </figure>

        <figure className="flex flex-col gap-y-1">
          <p className="ms-3 text-gray-600">간편 비밀번호 확인</p>
          <Input
            name="confirmPassword"
            type="password"
            className="w-full border border-gray-300 text-sm"
            onClick={() => {
              setActiveField('confirmPassword');
              setShowKeyPad(true);
            }}
            onChange={handleKeyboardChange('confirmPassword')}
            value={confirmPin}
            placeholder="비밀번호 6자리 확인"
            maxLength={6}
            required
          />
          {confirmPasswordErrorMessage && (
            <p className="break-keep p-3 text-sm text-red-500">
              {confirmPasswordErrorMessage}
            </p>
          )}
        </figure>

        {isPasswordMatch(formData.pin, confirmPin) && (
          <div className="py-10">
            <Button label="회원가입" variant="primary" onClick={handleSubmit} />
          </div>
        )}

        {showKeyPad && (
          <div className="py-10">
            <KeyPad variant="password" onKeyPress={handleKeyPress} />
          </div>
        )}
      </article>
    </main>
  );
};

export default UserSignUp3;
