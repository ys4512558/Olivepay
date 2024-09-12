import { useState } from 'react';
import { Button, Input } from '../common';
import PasswordCheck from './PasswordCheck';
import { patchNickname } from '../../api/userApi';

interface NicknameChangeProps {
  closeModal: () => void;
}

const NicknameChange: React.FC<NicknameChangeProps> = ({ closeModal }) => {
  const [step, setStep] = useState<number>(1);
  const [password, setPassword] = useState<string>('');
  const [newNickname, setNewNickname] = useState<string>('');

  const handleStep = () => {
    // 나중에 비밀번호 확인 성공 시 스텝 변경
    setStep(2);
  };

  const handleChange = () => {
    console.log('닉네임 변경 요청');
    // 성공하면 모달 닫기
    // patchNickname(newNickname).then(() => {
    //   closeModal();
    // });
  };

  return (
    <>
      {step === 1 ? (
        <PasswordCheck
          label="닉네임"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          onClick={handleStep}
        />
      ) : (
        <div className="mt-8 flex flex-col gap-6">
          <p>
            📌 닉네임은{' '}
            <span className="mr-1 text-xl font-semibold">10자 이내</span>로 변경
            가능합니다.
          </p>
          <Input
            className="mt-8"
            value={newNickname}
            onChange={(e) => setNewNickname(e.target.value)}
          />
          <div className="flex gap-2">
            <Button label="취소" className="bg-BASE" onClick={closeModal} />
            <Button label="변경" onClick={handleChange} />
          </div>
        </div>
      )}
    </>
  );
};

export default NicknameChange;
