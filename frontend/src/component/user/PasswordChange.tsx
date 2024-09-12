import { useState } from 'react';

import PasswordCheck from './PasswordCheck';

const PasswordChange = () => {
  const [step, setStep] = useState<number>(1);
  const [password, setPassword] = useState<string>('');

  const handleStep = () => {
    // 나중에 비밀번호 확인 성공 시 스텝 변경
    setStep(2);
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
        <div>s</div>
      )}
    </>
  );
};

export default PasswordChange;
