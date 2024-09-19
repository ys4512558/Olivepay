import { Button, Input } from '../common';
import { KeyIcon } from '@heroicons/react/24/solid';

const PasswordCheck: React.FC<PasswordCheckProps> = ({
  label,
  value,
  onChange,
  onClick,
}) => {
  return (
    <div className="mx-2 mt-8 flex flex-col gap-4">
      <div className="mb-8 ml-2 flex items-center gap-4">
        <KeyIcon className="size-8 text-PRIMARY" />
        <p className="text-DARKBASE">
          {label}을 변경하기 위해선 <br />{' '}
          <span className="mr-1 text-xl text-black">비밀번호 확인</span>이
          필요합니다.
        </p>
      </div>
      <Input value={value} onChange={onChange} type="password" />
      <Button label="확인" onClick={onClick} />
    </div>
  );
};

export default PasswordCheck;
