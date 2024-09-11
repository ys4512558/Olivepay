import { useState } from 'react';
import { Input, Button, PageTitle, BackButton } from '../component/common';

const CardScan: React.FC<CardScanProps> = () => {
  const [allChecked, setAllChecked] = useState(false);
  const [termsChecked, setTermsChecked] = useState({
    term1: false,
    term2: false,
    term3: false,
  });

  const [cardNum, setCardNum] = useState({
    card1: '',
    card2: '',
    card3: '',
    card4: '',
  });

  const [expiryMM, setExpiryMM] = useState('');
  const [expiryYY, setExpiryYY] = useState('');
  const [cvc, setCvc] = useState('');
  const [cardPassword, setCardPassword] = useState('');

  const [errors, setErrors] = useState({
    cardNumError: '',
    expiryError: '',
    cvcError: '',
    cardPasswordError: '',
  });

  // 전체 체크박스 처리
  const handleAllChecked = () => {
    const newChecked = !allChecked;
    setAllChecked(newChecked);
    setTermsChecked({
      term1: newChecked,
      term2: newChecked,
      term3: newChecked,
    });
  };

  const handleTermChange = (term: keyof typeof termsChecked) => {
    const updatedTerms = {
      ...termsChecked,
      [term]: !termsChecked[term],
    };
    setTermsChecked(updatedTerms);
    const allSelected = Object.values(updatedTerms).every(
      (value) => value === true,
    );
    setAllChecked(allSelected);
  };

  const validateFields = () => {
    let valid = true;
    const newErrors = {
      cardNumError: '',
      expiryError: '',
      cvcError: '',
      cardPasswordError: '',
    };

    if (
      cardNum.card1.length !== 4 ||
      cardNum.card2.length !== 4 ||
      cardNum.card3.length !== 4 ||
      cardNum.card4.length !== 4
    ) {
      newErrors.cardNumError = '카드 번호는 각각 4자리 숫자여야 합니다.';
      valid = false;
    }

    const month = parseInt(expiryMM, 10);
    const year = parseInt(expiryYY, 10);
    if (
      expiryMM.length !== 2 ||
      expiryYY.length !== 2 ||
      month < 1 ||
      month > 12 ||
      year < 24
    ) {
      newErrors.expiryError = '유효기간을 정확하게 작성해주세요.';
      valid = false;
    }

    if (cvc.length !== 3) {
      newErrors.cvcError = 'CVC는 3자리 숫자여야 합니다.';
      valid = false;
    }

    if (cardPassword.length !== 2) {
      newErrors.cardPasswordError = '비밀번호는 2자리 숫자여야 합니다.';
      valid = false;
    }

    setErrors(newErrors);
    return valid;
  };

  // 제출 처리
  const handleSubmit = () => {
    if (!validateFields()) {
      return;
    }

    const realCardNum = `${cardNum.card1}${cardNum.card2}${cardNum.card3}${cardNum.card4}`;
    const expirationYear = expiryYY;
    const expirationMonth = expiryMM;

    const data = {
      // userId
      realCardNum,
      expirationYear,
      expirationMonth,
      cvc,
      creditPassword: cardPassword,
    };

    console.log('API로 보낼 데이터:', data);

    // API 호출
  };

  return (
    <main>
      <header className="flex w-full items-center justify-between p-10 px-10 pb-10 pt-24">
        <BackButton />
        <div className="flex-grow text-center">
          <PageTitle title="카드 등록" />
        </div>
      </header>

      <article className="flex flex-col gap-y-6 p-5">
        <div className="text-right">
          <Button
            label="카드스캔"
            variant="secondary"
            className="px-4 py-2 text-sm font-bold"
          />
        </div>

        <figure className="flex flex-col gap-y-1">
          <p className="ms-3 text-gray-600">카드 번호</p>
          <div className="grid grid-cols-4 gap-2">
            <Input
              name="card1"
              value={cardNum.card1}
              onChange={(e) =>
                setCardNum({ ...cardNum, card1: e.target.value })
              }
              className="border border-gray-300 text-center text-sm"
              placeholder="0000"
              maxLength={4}
            />
            <Input
              name="card2"
              value={cardNum.card2}
              onChange={(e) =>
                setCardNum({ ...cardNum, card2: e.target.value })
              }
              className="border border-gray-300 text-center text-sm"
              placeholder="0000"
              maxLength={4}
            />
            <Input
              name="card3"
              value={cardNum.card3}
              onChange={(e) =>
                setCardNum({ ...cardNum, card3: e.target.value })
              }
              className="border border-gray-300 text-center text-sm"
              placeholder="0000"
              maxLength={4}
            />
            <Input
              name="card4"
              value={cardNum.card4}
              onChange={(e) =>
                setCardNum({ ...cardNum, card4: e.target.value })
              }
              className="border border-gray-300 text-center text-sm"
              placeholder="0000"
              maxLength={4}
            />
          </div>
          {errors.cardNumError && (
            <p className="p-3 text-sm text-red-500">{errors.cardNumError}</p>
          )}
        </figure>

        <figure className="grid grid-cols-2 gap-4">
          <div className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">유효기간</p>
            <div className="grid grid-cols-2 gap-2">
              <Input
                name="expiryMM"
                value={expiryMM}
                onChange={(e) => setExpiryMM(e.target.value)}
                className="border border-gray-300 p-3 text-center text-sm"
                placeholder="MM"
                maxLength={2}
              />
              <Input
                name="expiryYY"
                value={expiryYY}
                onChange={(e) => setExpiryYY(e.target.value)}
                className="border border-gray-300 p-3 text-center text-sm"
                placeholder="YY"
                maxLength={2}
              />
            </div>
            {errors.expiryError && (
              <p className="p-3 text-sm text-red-500">{errors.expiryError}</p>
            )}
          </div>

          <div className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">CVC 번호</p>
            <Input
              name="cvc"
              value={cvc}
              onChange={(e) => setCvc(e.target.value)}
              className="w-full border border-gray-300 p-3 text-center text-sm"
              placeholder="카드 뒷면 3자리 숫자"
              maxLength={3}
            />
            {errors.cvcError && (
              <p className="p-3 text-sm text-red-500">{errors.cvcError}</p>
            )}
          </div>
        </figure>

        <figure className="flex flex-col gap-y-1">
          <p className="ms-3 text-gray-600">카드 비밀번호</p>
          <Input
            name="cardPassword"
            type="password"
            value={cardPassword}
            onChange={(e) => setCardPassword(e.target.value)}
            className="border border-gray-300 p-3 text-sm"
            placeholder="비밀번호 앞 2자리 숫자"
            maxLength={2}
          />
          {errors.cardPasswordError && (
            <p className="p-3 text-sm text-red-500">
              {errors.cardPasswordError}
            </p>
          )}
        </figure>

        <figure className="border border-gray-300 p-4">
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
            label="카드 등록하기"
            variant="primary"
            className="w-full py-3 text-white"
            onClick={handleSubmit} // 등록 버튼 클릭 시 API 데이터 준비
          />
        </div>
      </article>
    </main>
  );
};

export default CardScan;
