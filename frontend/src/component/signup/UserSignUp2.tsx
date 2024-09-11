import { Input, Button } from '../common';
import { ChangeEvent, useEffect, useRef, useState } from 'react';
import * as Yup from 'yup';
import { ValidationError } from 'yup';

const UserSignUp2: React.FC<UserSignUpProps> = ({
  setStep,
  handleFormDataChange,
  formData,
}) => {
  const nameInputRef = useRef<HTMLInputElement>(null);
  const [birthdateError, setBirthdateError] = useState<string>('');

  useEffect(() => {
    if (nameInputRef.current) {
      nameInputRef.current.focus();
    }
  }, []);

  const formatBirthdate = (value: string) => {
    const cleaned = value.replace(/\D/g, '');
    if (cleaned.length === 8) {
      return `${cleaned.slice(0, 4)}.${cleaned.slice(4, 6)}.${cleaned.slice(
        6,
        8,
      )}`;
    }
    return value;
  };

  const validationSchema = Yup.object().shape({
    birthdate: Yup.string()
      .matches(
        /^\d{4}\.\d{2}\.\d{2}$/,
        '생년월일은 YYYY.MM.DD 형식이어야 합니다.',
      )
      .test('isValidDate', '유효한 날짜를 입력해 주세요.', (value) => {
        if (!value) return false;
        const cleaned = value.replace(/\D/g, '');
        const year = parseInt(cleaned.slice(0, 4), 10);
        const month = parseInt(cleaned.slice(4, 6), 10);
        const day = parseInt(cleaned.slice(6, 8), 10);
        return (
          month >= 1 && month <= 12 && day >= 1 && day <= 31 && year <= 2024
        );
      })
      .required('생년월일을 입력해 주세요.'),
  });

  const validateForm = async (): Promise<boolean> => {
    try {
      await validationSchema.validate(
        { birthdate: formData.birthdate },
        { abortEarly: false },
      );
      setBirthdateError('');
      return true;
    } catch (error) {
      if (error instanceof ValidationError) {
        setBirthdateError(error.errors[0]);
      }
      return false;
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    if (name === 'birthdate') {
      const formattedDate = formatBirthdate(value);
      handleFormDataChange(name, formattedDate);
    } else {
      handleFormDataChange(name, value);
    }
  };

  const goToNextStep = async (e: React.FormEvent) => {
    e.preventDefault();
    const isValid = await validateForm();
    if (isValid) {
      setStep(3);
    }
  };

  return (
    <main>
      <form onSubmit={goToNextStep}>
        <article className="flex flex-col gap-y-6 p-5">
          <figure className="flex gap-x-6">
            <div>
              <p className="ms-3 text-gray-600">이름</p>
              <Input
                ref={nameInputRef}
                name="name"
                value={formData.name}
                className="w-full border border-gray-300"
                onChange={handleChange}
                maxLength={30}
                required
              />
            </div>
            <div>
              <p className="ms-3 text-gray-600">닉네임</p>
              <Input
                name="nickname"
                value={formData.nickname}
                className="w-full border border-gray-300"
                onChange={handleChange}
                maxLength={10}
                required
              />
            </div>
          </figure>

          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">생년월일</p>
            <Input
              name="birthdate"
              value={formData.birthdate}
              className="w-full border border-gray-300"
              onChange={handleChange}
              maxLength={10} // YYYY.MM.DD 형식
              required
            />
            {birthdateError && (
              <p className="p-3 text-sm text-red-500">{birthdateError}</p>
            )}
          </figure>

          <div className="py-10">
            <Button
              label="다음으로"
              variant="primary"
              className="w-full"
              type="submit"
            />
          </div>
        </article>
      </form>
    </main>
  );
};

export default UserSignUp2;
