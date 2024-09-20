import { Input, Button, Layout } from '../common';
import { ChangeEvent, useEffect, useRef, useState } from 'react';
import { ValidationError } from 'yup';
import {
  ONLY_NUMERIC_REGEX,
  validateName,
  validationSchema,
  formatBirthdate,
} from '../../utils/validators';

const UserSignUp2: React.FC<UserSignUpProps> = ({
  setStep,
  handleFormDataChange,
  formData1,
}) => {
  const nameInputRef = useRef<HTMLInputElement>(null);
  const [birthdateError, setBirthdateError] = useState<string>('');
  const [nameError, setNameError] = useState<string>('');

  useEffect(() => {
    if (nameInputRef.current) {
      nameInputRef.current.focus();
    }
  }, []);

  const validateForm = async (): Promise<boolean> => {
    try {
      await validationSchema.validate(
        { birthdate: formData1.birthdate },
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

    if (name === 'name') {
      const { isValid, error } = validateName(value);
      setNameError(error);
      if (!isValid) return;
    }

    if (name === 'birthdate') {
      if (!ONLY_NUMERIC_REGEX.test(value.replace(/\./g, ''))) {
        setBirthdateError('숫자만 입력 가능합니다.');
        return;
      } else {
        setBirthdateError('');
      }
      const formattedDate = formatBirthdate(value);
      handleFormDataChange(name, formattedDate, 'formData1');
    } else {
      handleFormDataChange(name, value, 'formData1');
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
    <Layout>
      <main>
        <form onSubmit={goToNextStep}>
          <article className="flex flex-col gap-y-6 p-5">
            <figure className="flex gap-x-6">
              <div>
                <p className="ms-3 text-gray-600">이름</p>
                <Input
                  ref={nameInputRef}
                  name="name"
                  value={formData1.name}
                  className="w-full border border-gray-300"
                  onChange={handleChange}
                  maxLength={30}
                  required
                />
                {nameError && (
                  <p className="break-keep p-3 text-sm text-red-500">
                    {nameError}
                  </p>
                )}
              </div>
              <div>
                <p className="ms-3 text-gray-600">닉네임</p>
                <Input
                  name="nickname"
                  value={formData1.nickname}
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
                value={formData1.birthdate}
                className="w-full border border-gray-300"
                onChange={handleChange}
                maxLength={10}
                required
              />
              {birthdateError && (
                <p className="break-keep p-3 text-sm text-red-500">
                  {birthdateError}
                </p>
              )}
            </figure>

            <div className="py-10">
              <Button label="다음으로" variant="primary" type="submit" />
            </div>
          </article>
        </form>
      </main>
    </Layout>
  );
};

export default UserSignUp2;
