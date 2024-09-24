import { Input, Button, KeyPad } from '../../component/common';
import { useState } from 'react';
import { Donate3Props } from '../../types/donate';

const Donate3: React.FC<Donate3Props> = ({
  onNext,
  accountNumber,
  setAccountNumber,
}) => {
  const [showKeyPad, setShowKeyPad] = useState(false);

  const handleKeyPress = (value: string | number) => {
    if (value === 'delete') {
      setAccountNumber(accountNumber.slice(0, -1));
    } else if (accountNumber.length < 20) {
      setAccountNumber(accountNumber + value);
    }
  };

  return (
    <main className="p-10">
      <figure className="mb-10 ml-3 flex flex-col gap-y-10">
        <p className="font-semibold">내 계좌정보 입력</p>
        <div className="flex items-center gap-x-4">
          <img
            src="/logo.png"
            alt="Logo"
            className="h-20 w-20 rounded-full border"
          />
          <p className="text-xl font-bold">싸피뱅크</p>
        </div>
      </figure>
      <div className="pb-5">
        <Input
          name="AccountNumber"
          value={accountNumber}
          onClick={() => setShowKeyPad(true)}
          onChange={(e) => setAccountNumber(e.target.value)}
          className="w-full border border-gray-300"
          placeholder="내 계좌번호 입력"
          required
        />
      </div>
      {showKeyPad && (
        <div className="py-5">
          <KeyPad variant="money" onKeyPress={handleKeyPress} />
        </div>
      )}
      <div className="py-10">
        <Button label="다음으로" variant="primary" onClick={onNext} />
      </div>
    </main>
  );
};

export default Donate3;
