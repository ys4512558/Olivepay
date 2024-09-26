import { Input, Button, KeyPad } from '../../component/common';
import { useState } from 'react';
import { CommonProps } from '../../types/donate';

const Donate3: React.FC<CommonProps> = ({
  onNext,
  donateInfo,
  setDonateInfo,
}) => {
  const { accountNumber } = donateInfo;
  const [showKeyPad, setShowKeyPad] = useState(false);

  const handleKeyPress = (value: string | number) => {
    if (value === 'delete') {
      setDonateInfo((prevInfo) => ({
        ...prevInfo,
        accountNumber: accountNumber.slice(0, -1),
      }));
    } else if (accountNumber.length < 20) {
      setDonateInfo((prevInfo) => ({
        ...prevInfo,
        accountNumber: accountNumber + value,
      }));
    }
  };

  return (
    <main className="px-10 py-5">
      <figure className="mb-10 ml-3 flex flex-col gap-y-6">
        <p className="text-md font-semibold">내 계좌정보 입력</p>
        <div className="flex items-center gap-x-4">
          <img
            src="/logo.png"
            alt="Logo"
            className="h-20 w-20 rounded-full border"
          />
          <p className="text-xl font-bold">싸피뱅크</p>
        </div>
      </figure>
      <div className="pb-3">
        <Input
          name="AccountNumber"
          value={accountNumber}
          onClick={() => setShowKeyPad(true)}
          onChange={(e) =>
            setDonateInfo((prevInfo) => ({
              ...prevInfo,
              accountNumber: e.target.value,
            }))
          }
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
      <div className="pb-20 pt-5">
        <Button label="다음으로" variant="primary" onClick={onNext} />
      </div>
    </main>
  );
};

export default Donate3;
