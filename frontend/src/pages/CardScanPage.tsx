import { useState, useRef } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import {
  Input,
  Button,
  PageTitle,
  BackButton,
  Layout,
} from '../component/common';
import { isNumeric } from '../utils/validators';
import { Helmet } from 'react-helmet';
import { registerCard } from '../api/cardApi';
import { useSnackbar } from 'notistack';
import { userLogin } from '../api/loginApi';
import axios from 'axios';

const terms = [
  { id: 1, title: '올리브페이 개인(신용)정보 수집 및 이용 동의' },
  { id: 2, title: '올리브페이 ➡ 카드사 개인(신용)정보 제공 동의' },
  { id: 3, title: '카드사 ➡ 올리브페이 개인(신용)정보 제공 동의' },
];

const CardScan: React.FC<CardScanProps> = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { phoneNumber, userPw } = location.state || {};
  const { enqueueSnackbar } = useSnackbar();
  const [allChecked, setAllChecked] = useState(false);
  const [termsChecked, setTermsChecked] = useState({
    term1: false,
    term2: false,
    term3: false,
  });
  const [cardNumbers, setCardNumbers] = useState(['', '', '', '']);
  const cardRefs = useRef<(HTMLInputElement | null)[]>([]);
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

  const handleCardNumberChange = (
    index: number,
    e: React.ChangeEvent<HTMLInputElement>,
  ) => {
    const value = e.target.value;
    if (!/^\d*$/.test(value)) {
      setErrors({ ...errors, cardNumError: '숫자만 입력 가능합니다.' });
    } else {
      setErrors({ ...errors, cardNumError: '' });
      const newCardNumbers = [...cardNumbers];
      newCardNumbers[index] = value.slice(0, 4);
      setCardNumbers(newCardNumbers);
      if (value.length === 4 && index < 3 && cardRefs.current[index + 1]) {
        cardRefs.current[index + 1]?.focus();
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

    if (cardNumbers.some((num) => num.length !== 4)) {
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

    const realCardNum = cardNumbers.join('');
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
      const response = await registerCard(
        realCardNum,
        expirationYear,
        expirationMonth,
        cvc,
        cardPassword,
      );
      enqueueSnackbar(`${response.message}`, {
        variant: 'success',
      });
      userLogin(phoneNumber, userPw);
      navigate('/home');
    } catch (error) {
      if (axios.isAxiosError(error)) {
        if (error.code === 'BAD_REQUEST') {
          enqueueSnackbar(
            `${error.response?.data?.data || '알 수 없는 오류가 발생했습니다.'}`,
            {
              variant: 'error',
            },
          );
        } else {
          enqueueSnackbar(
            `${error.response?.data?.message || '알 수 없는 오류가 발생했습니다.'}`,
            {
              variant: 'error',
            },
          );
        }
      }
    }
  };

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="유저가 결제에 사용할 카드를 등록할 수 있습니다."
        />
      </Helmet>
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
                {cardNumbers.map((number, index) => (
                  <Input
                    key={index}
                    name={`card${index + 1}`}
                    value={number}
                    onChange={(e) => handleCardNumberChange(index, e)}
                    className="border border-gray-300 text-center text-sm"
                    placeholder="0000"
                    maxLength={4}
                    ref={(el) => (cardRefs.current[index] = el)}
                  />
                ))}
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
    </>
  );
};

export default CardScan;
