import { useState } from 'react';
import { Input, Button } from '../common';

const CardScan: React.FC<UserSignUpProps> = ({ setStep }) => {
  const goToNextStep = () => {
    setStep(4);
  };

  // 체크 상태를 관리하는 state
  const [allChecked, setAllChecked] = useState(false);
  const [termsChecked, setTermsChecked] = useState({
    term1: false,
    term2: false,
    term3: false,
  });

  // 전체 체크박스를 토글하는 함수
  const handleAllChecked = () => {
    const newChecked = !allChecked;
    setAllChecked(newChecked);
    setTermsChecked({
      term1: newChecked,
      term2: newChecked,
      term3: newChecked,
    });
  };

  // 개별 체크박스를 토글하는 함수
  const handleTermChange = (term: keyof typeof termsChecked) => {
    const updatedTerms = {
      ...termsChecked,
      [term]: !termsChecked[term],
    };
    setTermsChecked(updatedTerms);

    // 모든 개별 약관이 체크된 경우 전체 체크박스도 체크
    const allSelected = Object.values(updatedTerms).every(
      (value) => value === true,
    );
    setAllChecked(allSelected);
  };

  return (
    <main>
      <article className="flex flex-col gap-y-6 p-5">
        <div className="text-right">
          <Button
            label="카드스캔"
            variant="secondary"
            className="px-4 py-2 text-sm font-bold"
          />
        </div>

        <figure className="flex flex-col gap-y-2">
          <p className="text-gray-600">카드 번호</p>
          <div className="grid grid-cols-4 gap-2">
            <Input
              name="card1"
              className="border border-gray-300 text-center text-sm"
              placeholder="0000"
            />
            <Input
              name="card2"
              className="border border-gray-300 text-center text-sm"
              placeholder="0000"
            />
            <Input
              name="card3"
              className="border border-gray-300 text-center text-sm"
              placeholder="0000"
            />
            <Input
              name="card4"
              className="border border-gray-300 text-center text-sm"
              placeholder="0000"
            />
          </div>
        </figure>

        <figure className="grid grid-cols-2 gap-4">
          <div className="flex flex-col">
            <p className="text-gray-600">유효기간</p>
            <div className="grid grid-cols-2 gap-2">
              <Input
                name="expiryMM"
                className="border border-gray-300 p-3 text-center text-sm"
                placeholder="MM"
              />
              <Input
                name="expiryYY"
                className="border border-gray-300 p-3 text-center text-sm"
                placeholder="YY"
              />
            </div>
          </div>

          <div className="flex flex-col">
            <p className="text-gray-600">CVC 번호</p>
            <Input
              name="cvc"
              className="w-full border border-gray-300 p-3 text-center text-sm"
              placeholder="카드 뒷면 3자리 숫자"
            />
          </div>
        </figure>

        <figure className="flex flex-col gap-y-1">
          <p className="text-gray-600">카드 비밀번호</p>
          <Input
            name="cardPassword"
            type="password"
            className="border border-gray-300 p-3 text-sm"
            placeholder="비밀번호 앞 2자리 숫자"
          />
        </figure>

        {/* 약관 동의 */}
        <figure className="rounded-sm border border-gray-300 p-4">
          <div className="mb-2 flex items-center justify-between border-b border-gray-300 pb-2">
            <div className="flex items-center">
              <input
                type="checkbox"
                checked={allChecked}
                onChange={handleAllChecked}
                className="mr-2"
              />
              <p className="font-bold text-gray-600">전체 약관 동의</p>
            </div>
          </div>

          <div className="flex flex-col gap-y-2">
            <div className="flex items-center">
              <input
                type="checkbox"
                checked={termsChecked.term1}
                onChange={() => handleTermChange('term1')}
                className="mr-2"
              />
              <p className="text-gray-600">약관 내용1</p>
            </div>
            <div className="flex items-center">
              <input
                type="checkbox"
                checked={termsChecked.term2}
                onChange={() => handleTermChange('term2')}
                className="mr-2"
              />
              <p className="text-gray-600">약관 내용2</p>
            </div>
            <div className="flex items-center">
              <input
                type="checkbox"
                checked={termsChecked.term3}
                onChange={() => handleTermChange('term3')}
                className="mr-2"
              />
              <p className="text-gray-600">약관 내용3</p>
            </div>
          </div>
        </figure>
        <div className="py-10">
          <Button
            label="다음으로"
            variant="primary"
            className="w-full rounded-full py-3 text-white"
            onClick={goToNextStep}
          />
        </div>
      </article>
    </main>
  );
};

export default CardScan;
