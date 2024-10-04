import { useState, useRef } from 'react';
import { Input, KeyPad, Button } from '../common';

const CheckPinCode: React.FC<CheckPinCodeProps> = ({ handlePaySuccess }) => {
  const [pin, setPin] = useState<string[]>(['', '', '', '', '', '']);
  const [iconPin, setIconPin] = useState<string[]>(['', '', '', '', '', '']);
  const inputRefs = useRef<(HTMLInputElement | null)[]>([]);

  const randomFoodIcon = () => {
    const icons = [
      '🍕',
      '🍔',
      '🍟',
      '🌭',
      '🍿',
      '🥓',
      '🧇',
      '🥞',
      '🍞',
      '🍖',
      '🥩',
      '🍚',
      '🍜',
      '🍣',
      '🍤',
      '🥣',
      '🍝',
      '🥘',
      '🍦',
      '🍩',
      '🍰',
      '🍫',
      '🍮',
    ];

    const randomIndex = Math.floor(Math.random() * icons.length);
    return icons[randomIndex];
  };

  const handleKeyPress = (num: number | string) => {
    if (num === 'delete') {
      const lastIndex = pin.findIndex((value) => value === '');
      const targetIndex = lastIndex === -1 ? pin.length - 1 : lastIndex - 1;
      const newPin = [...pin];
      const newIconPin = [...iconPin];
      newPin[targetIndex] = '';
      newIconPin[targetIndex] = '';
      setPin(newPin);
      setIconPin(newIconPin);

      if (targetIndex >= 0 && inputRefs.current[targetIndex]) {
        inputRefs.current[targetIndex]?.focus();
      }
      return;
    }

    const index = pin.findIndex((value) => value === '');
    if (index !== -1) {
      const newPin = [...pin];
      const newIconPin = [...iconPin];
      newPin[index] = num.toString();
      newIconPin[index] = randomFoodIcon();
      setPin(newPin);
      setIconPin(newIconPin);

      if (inputRefs.current[index + 1]) {
        inputRefs.current[index + 1]?.focus();
      }
    }
  };

  const handleBackspace = (index: number) => {
    if (pin[index] === '') {
      const prevIndex = index - 1;
      if (prevIndex >= 0) {
        const newPin = [...pin];
        const newIconPin = [...iconPin];
        newPin[prevIndex] = '';
        newIconPin[prevIndex] = '';
        setPin(newPin);
        setIconPin(newIconPin);
        inputRefs.current[prevIndex]?.focus();
      }
    } else {
      const newPin = [...pin];
      const newIconPin = [...iconPin];
      newPin[index] = '';
      newIconPin[index] = '';
      setPin(newPin);
      setIconPin(newIconPin);
    }
  };

  return (
    <div className="mx-8">
      <h3 className="mt-4 text-center text-md font-bold text-DARKBASE">
        결제 비밀번호를 입력하세요.
      </h3>
      <div className="my-8 flex items-center justify-center gap-1">
        {pin.map((_, index) => (
          <Input
            key={index}
            ref={(el) => (inputRefs.current[index] = el)}
            className="w-full border-2 text-center text-sm"
            value={iconPin[index]}
            readOnly
            onKeyDown={(e) => {
              if (e.key === 'Backspace') {
                handleBackspace(index);
              }
            }}
          />
        ))}
      </div>
      <KeyPad variant="password" onKeyPress={handleKeyPress} />
      <Button
        label="결제하기"
        onClick={() => handlePaySuccess(pin.join(''))}
        className="mt-4"
      />
    </div>
  );
};

export default CheckPinCode;
