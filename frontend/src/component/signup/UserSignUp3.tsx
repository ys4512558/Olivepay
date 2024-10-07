import { Input, Button, KeyPad } from '../common';
import { useState, useEffect, useRef } from 'react';

const UserSignUp3: React.FC<UserSignUpProps> = ({
  formData1,
  handleFormDataChange,
  handleSubmit,
}) => {
  const [activeField, setActiveField] = useState<'pin' | 'confirmPin' | null>(
    'pin',
  );
  const [showKeyPad, setShowKeyPad] = useState(true);
  const [pin, setPin] = useState<string[]>(['', '', '', '', '', '']);
  const [maskedPin, setMaskedPin] = useState<string[]>([
    '',
    '',
    '',
    '',
    '',
    '',
  ]);
  const [confirmPin, setConfirmPin] = useState<string[]>([
    '',
    '',
    '',
    '',
    '',
    '',
  ]);
  const [maskedConfirmPin, setMaskedConfirmPin] = useState<string[]>([
    '',
    '',
    '',
    '',
    '',
    '',
  ]);
  const [confirmPinErrorMessage, setConfirmPinErrorMessage] = useState('');
  const inputRefs = useRef<(HTMLInputElement | null)[]>([]);

  useEffect(() => {
    if (inputRefs.current[0]) {
      inputRefs.current[0]?.focus();
    }
  }, []);

  // 핀 번호 입력 처리
  const handlePinInput = (num: number | string) => {
    setConfirmPinErrorMessage('');
    const index = pin.findIndex((value) => value === '');
    if (index !== -1) {
      const newPin = [...pin];
      const newMaskedPin = [...maskedPin];
      newPin[index] = num.toString();
      newMaskedPin[index] = '🔒';
      setPin(newPin);
      setMaskedPin(newMaskedPin);

      if (index === 5) {
        handleFormDataChange('pin', newPin.join(''), 'formData1');
        setTimeout(() => {
          setActiveField('confirmPin');
          setConfirmPin(['', '', '', '', '', '']);
          setMaskedConfirmPin(['', '', '', '', '', '']);
          inputRefs.current[0]?.focus();
        }, 300);
      } else {
        inputRefs.current[index + 1]?.focus();
      }
    }
  };

  // 비밀번호 확인 입력 처리
  const handleConfirmPinInput = (num: number | string) => {
    setConfirmPinErrorMessage('');
    const index = confirmPin.findIndex((value) => value === '');
    if (index !== -1) {
      const newConfirmPin = [...confirmPin];
      const newMaskedConfirmPin = [...maskedConfirmPin];
      newConfirmPin[index] = num.toString();
      newMaskedConfirmPin[index] = '🔒';
      setConfirmPin(newConfirmPin);
      setMaskedConfirmPin(newMaskedConfirmPin);

      if (index === 5) {
        if (formData1.pin === newConfirmPin.join('')) {
          setConfirmPinErrorMessage('');
          setShowKeyPad(false);
        } else {
          setConfirmPinErrorMessage('핀 번호가 일치하지 않습니다.');
          setTimeout(() => {
            setActiveField('pin');
            setConfirmPin(['', '', '', '', '', '']);
            setPin(['', '', '', '', '', '']);
            setMaskedPin(['', '', '', '', '', '']);
            handleFormDataChange('pin', '', 'formData1');
            inputRefs.current[0]?.focus();
            setShowKeyPad(true);
          }, 1000);
        }
      } else {
        inputRefs.current[index + 1]?.focus();
      }
    }
  };

  // 삭제 처리
  const handleDelete = () => {
    const currentField = activeField === 'pin' ? pin : confirmPin;
    const setField = activeField === 'pin' ? setPin : setConfirmPin;
    const maskedField = activeField === 'pin' ? maskedPin : maskedConfirmPin;
    const setMaskedField =
      activeField === 'pin' ? setMaskedPin : setMaskedConfirmPin;

    const index = currentField.findIndex((value) => value === '');
    const targetIndex = index === -1 ? currentField.length - 1 : index - 1;

    if (targetIndex >= 0) {
      const newField = [...currentField];
      const newMaskedField = [...maskedField];
      newField[targetIndex] = '';
      newMaskedField[targetIndex] = '';
      setField(newField);
      setMaskedField(newMaskedField);
      inputRefs.current[targetIndex]?.focus();
      if (!showKeyPad) {
        setShowKeyPad(true);
      }
    }
  };

  // 키패드 입력 처리
  const handleKeyPress = (num: number | string) => {
    if (num === 'delete') {
      handleDelete();
    } else if (activeField === 'pin') {
      handlePinInput(num);
    } else if (activeField === 'confirmPin') {
      handleConfirmPinInput(num);
    }
  };

  const isButtonVisible =
    pin.every((value) => value !== '') &&
    confirmPin.every((value) => value !== '') &&
    formData1.pin === confirmPin.join('');

  return (
    <main>
      <article className="mx-8">
        <h3 className="mt-4 text-center text-md font-bold text-DARKBASE">
          {activeField === 'pin'
            ? '간편 핀 번호 6자리를 입력해주세요'
            : '핀 번호를 다시 확인해주세요'}
        </h3>

        <figure>
          <div className="my-8 flex items-center justify-center gap-1">
            {(activeField === 'pin' ? maskedPin : maskedConfirmPin).map(
              (value, index) => (
                <Input
                  key={index}
                  ref={(el) => (inputRefs.current[index] = el)}
                  className="w-full border-2 text-center text-sm"
                  value={value}
                  readOnly
                  onKeyDown={(e) => {
                    if (e.key === 'Backspace') {
                      handleDelete();
                    }
                  }}
                />
              ),
            )}
          </div>
          {confirmPinErrorMessage && (
            <p className="break-keep text-center text-sm text-red-500">
              {confirmPinErrorMessage}
            </p>
          )}
        </figure>

        {isButtonVisible && (
          <div className="pb-20 pt-5">
            <Button
              label="일반유저로 회원가입"
              variant="primary"
              onClick={handleSubmit}
            />
          </div>
        )}

        {showKeyPad && (
          <KeyPad variant="password" onKeyPress={handleKeyPress} />
        )}
      </article>
    </main>
  );
};

export default UserSignUp3;
