import { useState, useRef } from 'react';
import {
  Input,
  Button,
  PageTitle,
  BackButton,
  Layout,
} from '../component/common';
import { isNumeric } from '../utils/validators';
import { registerCard } from '../api/cardApi';

const terms = [
  { id: 1, title: '올리브페이 개인(신용)정보 수집 및 이용 동의' },
  { id: 2, title: '올리브페이 ➡ 카드사 개인(신용)정보 제공 동의' },
  { id: 3, title: '카드사 ➡ 올리브페이 개인(신용)정보 제공 동의' },
];

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

  const card2Ref = useRef<HTMLInputElement>(null);
  const card3Ref = useRef<HTMLInputElement>(null);
  const card4Ref = useRef<HTMLInputElement>(null);
  const expiryYYRef = useRef<HTMLInputElement>(null);

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

  const handleCardNumberChange =
    (field: keyof typeof cardNum) =>
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const value = e.target.value;
      if (!isNumeric(value)) {
        setErrors({ ...errors, cardNumError: '숫자만 입력 가능합니다.' });
      } else {
        setErrors({ ...errors, cardNumError: '' });
        setCardNum({ ...cardNum, [field]: value.slice(0, 4) });

        if (value.length === 4) {
          if (field === 'card1' && card2Ref.current) {
            card2Ref.current.focus();
          } else if (field === 'card2' && card3Ref.current) {
            card3Ref.current.focus();
          } else if (field === 'card3' && card4Ref.current) {
            card4Ref.current.focus();
          }
        }
      }
    };

  const handleExpiryChange =
    (field: 'MM' | 'YY') => (e: React.ChangeEvent<HTMLInputElement>) => {
      const value = e.target.value;
      if (!isNumeric(value)) {
        setErrors({ ...errors, expiryError: '숫자만 입력 가능합니다.' });
      } else {
        setErrors({ ...errors, expiryError: '' });
        if (field === 'MM') {
          setExpiryMM(value.slice(0, 2));
          if (value.length === 2 && expiryYYRef.current) {
            expiryYYRef.current.focus();
          }
        } else {
          setExpiryYY(value.slice(0, 2));
        }
      }
    };

  const handleCvcChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    if (!isNumeric(value)) {
      setErrors({ ...errors, cvcError: '숫자만 입력 가능합니다.' });
    } else {
      setErrors({ ...errors, cvcError: '' });
      setCvc(value.slice(0, 3));
    }
  };

  const handleCardPasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    if (!isNumeric(value)) {
      setErrors({ ...errors, cardPasswordError: '숫자만 입력 가능합니다.' });
    } else {
      setErrors({ ...errors, cardPasswordError: '' });
      setCardPassword(value.slice(0, 2));
    }
  };

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

  const handleSubmit = async () => {
    if (!validateFields()) {
      return;
    }

    const realCardNum = `${cardNum.card1}${cardNum.card2}${cardNum.card3}${cardNum.card4}`;
    const expirationYear = expiryYY;
    const expirationMonth = expiryMM;

    const data = {
      realCardNum,
      expirationYear,
      expirationMonth,
      cvc,
      creditPassword: cardPassword,
    };

    console.log('API로 보낼 데이터:', data);

    try {
      // registerCard API 호출
      const response = await registerCard(
        realCardNum,
        expirationYear,
        expirationMonth,
        cvc,
        cardPassword
      );
      console.log('카드 등록 성공:', response);
  
      // 성공 시 처리 로직 (예: 성공 메시지 표시 또는 다음 페이지로 이동)
    } catch (error) {
      console.error('카드 등록 실패:', error);
      // 에러 처리 로직 (예: 사용자에게 에러 메시지 표시)
    }
  };

  return (
    <Layout>
      <main>
        <header className="flex w-full items-center justify-between px-10 pt-4">
          <BackButton />
          <div className="flex-grow text-center">
            <PageTitle title="카드 등록" />
          </div>
          <div className="w-8" />
        </header>

        <article className="flex flex-col gap-y-6 p-5">
          <div className="text-right">
            <Button
              label="카드스캔"
              variant="secondary"
              className="px-4 py-2 text-sm font-bold"
            />
          </div>

          <figure className="flex flex-col gap-y-2">
            <p className="ms-3 text-md font-semibold text-gray-600">
              카드 번호
            </p>
            <div className="grid grid-cols-4 gap-2">
              <Input
                name="card1"
                value={cardNum.card1}
                onChange={handleCardNumberChange('card1')}
                className="border border-gray-300 text-center text-sm"
                placeholder="0000"
                maxLength={4}
              />
              <Input
                name="card2"
                value={cardNum.card2}
                onChange={handleCardNumberChange('card2')}
                className="border border-gray-300 text-center text-sm"
                placeholder="0000"
                maxLength={4}
                ref={card2Ref}
              />
              <Input
                name="card3"
                value={cardNum.card3}
                onChange={handleCardNumberChange('card3')}
                className="border border-gray-300 text-center text-sm"
                placeholder="0000"
                maxLength={4}
                ref={card3Ref}
              />
              <Input
                name="card4"
                value={cardNum.card4}
                onChange={handleCardNumberChange('card4')}
                className="border border-gray-300 text-center text-sm"
                placeholder="0000"
                maxLength={4}
                ref={card4Ref}
              />
            </div>
            <div className="min-h-5">
              {errors.cardNumError && (
                <p className="mt-1 ps-3 text-sm text-red-500">
                  {errors.cardNumError}
                </p>
              )}
            </div>
          </figure>

          <figure className="grid grid-cols-2 gap-4">
            <div className="flex flex-col gap-y-2">
              <p className="ms-3 text-md font-semibold text-gray-600">
                유효기간
              </p>
              <div className="grid grid-cols-2 gap-2">
                <Input
                  name="expiryMM"
                  value={expiryMM}
                  onChange={handleExpiryChange('MM')}
                  className="border border-gray-300 p-3 text-center text-sm"
                  placeholder="MM"
                  maxLength={2}
                />
                <Input
                  name="expiryYY"
                  value={expiryYY}
                  onChange={handleExpiryChange('YY')}
                  className="border border-gray-300 p-3 text-center text-sm"
                  placeholder="YY"
                  maxLength={2}
                  ref={expiryYYRef}
                />
              </div>
              <div className="min-h-5">
                {errors.expiryError && (
                  <p className="mt-1 ps-3 text-sm text-red-500">
                    {errors.expiryError}
                  </p>
                )}
              </div>
            </div>

            <div className="flex flex-col gap-y-2">
              <p className="ms-3 text-md font-semibold text-gray-600">
                CVC 번호
              </p>
              <Input
                name="cvc"
                value={cvc}
                onChange={handleCvcChange}
                className="w-full border border-gray-300 p-3 text-center text-sm"
                placeholder="카드 뒷면 3자리 숫자"
                maxLength={3}
              />
              <div className="min-h-5">
                {errors.cvcError && (
                  <p className="mt-1 ps-3 text-sm text-red-500">
                    {errors.cvcError}
                  </p>
                )}
              </div>
            </div>
          </figure>

          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-md font-semibold text-gray-600">
              카드 비밀번호
            </p>
            <Input
              name="cardPassword"
              type="password"
              value={cardPassword}
              onChange={handleCardPasswordChange}
              className="border border-gray-300 p-3 text-sm"
              placeholder="비밀번호 앞 2자리 숫자"
              maxLength={2}
            />
            <div className="min-h-5">
              {errors.cardPasswordError && (
                <p className="mt-1 ps-3 text-sm text-red-500">
                  {errors.cardPasswordError}
                </p>
              )}
            </div>
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
                <p className="text-md font-bold font-semibold text-gray-600">
                  전체 약관 동의
                </p>
              </div>
            </div>

            <div className="flex flex-col gap-y-3">
              {terms.map((term) => (
                <div key={term.id} className="flex items-center">
                  <input
                    type="checkbox"
                    checked={
                      termsChecked[
                        `term${term.id}` as keyof typeof termsChecked
                      ]
                    }
                    onChange={() =>
                      handleTermChange(
                        `term${term.id}` as keyof typeof termsChecked,
                      )
                    }
                  />
                  <p className="text-base text-gray-600">{term.title}</p>
                </div>
              ))}
            </div>
          </figure>

          <div className="pb-20 pt-3">
            <Button
              label="카드 등록하기"
              variant="primary"
              className="w-full py-3 text-white"
              onClick={handleSubmit}
            />
          </div>
        </article>
      </main>
    </Layout>
  );
};

export default CardScan;
