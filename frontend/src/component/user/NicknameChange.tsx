import { useState, useCallback } from 'react';
import { Button, Input } from '../common';
import PasswordCheck from './PasswordCheck';
import { patchNickname, checkPassword } from '../../api/userApi';
import { useSnackbar } from 'notistack';
import { useAtom } from 'jotai';
import { userAtom } from '../../atoms';

const NicknameChange: React.FC<infoChangeProps> = ({ closeModal }) => {
  const { enqueueSnackbar } = useSnackbar();
  const [step, setStep] = useState<number>(1);
  const [password, setPassword] = useState<string>('');
  const [newNickname, setNewNickname] = useState<string>('');
  const [, setUser] = useAtom(userAtom);

  const handleStep = useCallback(() => {
    checkPassword(password)
      .then(() => setStep(2))
      .catch(() => {
        enqueueSnackbar('비밀번호가 일치하지 않습니다.', {
          variant: 'error',
        });
        setPassword('');
      });
  }, [password, enqueueSnackbar]);

  const handleChange = useCallback(() => {
    patchNickname(newNickname).then(() => {
      closeModal();
      setUser((prevUser) => ({
        ...prevUser,
        nickname: newNickname,
      }));
      enqueueSnackbar('닉네임 변경이 완료되었습니다.', {
        variant: 'success',
      });
    });
  }, [newNickname, closeModal, setUser, enqueueSnackbar]);

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
        <div className="mt-8 flex flex-col gap-6 text-base">
          <p>
            📌 닉네임은
            <span className="mr-1 text-lg font-semibold"> 6자 이내</span>로 변경
            가능합니다.
          </p>
          <Input
            className="mt-8"
            value={newNickname}
            onChange={(e) => setNewNickname(e.target.value)}
            maxLength={6}
          />
          <div className="flex gap-2">
            <Button label="취소" className="bg-gray-500" onClick={closeModal} />
            <Button label="변경" onClick={handleChange} />
          </div>
        </div>
      )}
    </>
  );
};

export default NicknameChange;
