import { useState } from 'react';
import Select, { StylesConfig, GroupBase, SingleValue } from 'react-select';
import { Input, Button } from '../common';
import {
  numericRegex,
  certificateRegistrationNumberRegex,
  seoulRegex,
  otherRegionsRegex,
  formatTelephoneNumber,
} from '../../utils/validators';

interface CategoryOption {
  value: string;
  label: string;
}
const customStyles: StylesConfig<
  CategoryOption,
  false,
  GroupBase<CategoryOption>
> = {
  control: (provided) => ({
    ...provided,
    height: '3.5rem',
    borderRadius: '30px',
    border: 'none',
    boxShadow:
      '0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08)',
    padding: '0 1rem',
    fontSize: 'text-md',
  }),
  menu: (provided) => ({
    ...provided,
    boxShadow: '0 4px 8px rgba(50, 50, 93, 0.25)',
  }),
  option: (provided, state) => ({
    ...provided,
    padding: '10px 20px',
    backgroundColor: state.isSelected ? '#99BBA2' : 'white',
    color: state.isSelected ? 'white' : 'black',
    fontWeight: 'normal',
  }),
};

const categoryOptions: CategoryOption[] = [
  { value: '한식', label: '한식' },
  { value: '양식', label: '양식' },
  { value: '중식', label: '중식' },
  { value: '일식', label: '일식' },
  { value: '패스트푸드', label: '패스트푸드' },
  { value: '제과점', label: '제과점' },
  { value: '일반대중음식', label: '일반대중음식' },
  { value: '할인점/슈퍼마켓', label: '할인점/슈퍼마켓' },
  { value: '기타', label: '기타' },
];

const UserSignUp4: React.FC<UserSignUpProps> = ({
  formData2,
  handleFormDataChange,
  handleSubmit,
}) => {
  const [registrationNumberError, setRegistrationNumberError] = useState('');
  const [telephoneNumberError, setTelephoneNumberError] = useState('');
  const [fileError, setFileError] = useState('');
  const [category, setCategory] = useState<CategoryOption | null>(
    categoryOptions[0],
  );

  const handleCategoryChange = (
    selectedOption: SingleValue<CategoryOption>,
  ) => {
    setCategory(selectedOption);
    if (selectedOption) {
      handleFormDataChange('category', selectedOption.value, 'formData2');
    }
  };
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFileError('');
    if (e.target.files && e.target.files[0]) {
      const file = e.target.files[0];
      const validTypes = ['.pdf', '.jpg', '.jpeg', '.png'];
      const fileExtension = file.name.split('.').pop()?.toLowerCase() || '';

      if (!validTypes.includes(`.${fileExtension}`)) {
        setFileError(
          '지원되지 않는 파일 형식입니다. PDF, JPG, JPEG, PNG 형식만 가능합니다.',
        );
      }
    }
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    const { name, value } = e.target;

    if (name === 'franchiseName' || name === 'category') {
      handleFormDataChange(name, value, 'formData2');
      return;
    }

    if (!numericRegex.test(value)) {
      if (name === 'registrationNumber') {
        setRegistrationNumberError('숫자만 입력 가능합니다.');
      } else if (name === 'telephoneNumber') {
        setTelephoneNumberError('숫자만 입력 가능합니다.');
      }
      return;
    }
    if (name === 'telephoneNumber') {
      const formattedTelephone = formatTelephoneNumber(value);

      if (
        !seoulRegex.test(formattedTelephone) &&
        !otherRegionsRegex.test(formattedTelephone)
      ) {
        setTelephoneNumberError(
          '유효하지 않은 전화번호 형식입니다. 형식: 02-XXX-XXXX 또는 0XX-XXX-XXXX',
        );
      } else {
        setTelephoneNumberError('');
      }
      handleFormDataChange(name, formattedTelephone, 'formData2');
    } else if (name === 'registrationNumber') {
      if (!certificateRegistrationNumberRegex.test(value)) {
        setRegistrationNumberError('사업자등록번호는 10자리 숫자여야 합니다.');
      } else {
        setRegistrationNumberError('');
      }
      handleFormDataChange(name, value, 'formData2');
    }
  };

  return (
    <main>
      <article className="flex flex-col gap-y-6 p-10">
        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">
            사업자등록번호
          </p>
          <Input
            name="registrationNumber"
            value={formData2.registrationNumber}
            className="border border-gray-300 px-4"
            required
            onChange={handleChange}
            maxLength={10}
          />
          {registrationNumberError && (
            <p className="break-keep p-3 text-sm text-red-500">
              {registrationNumberError}
            </p>
          )}
        </figure>

        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">
            사업자등록증 첨부
          </p>
          <div className="ps-3 pt-3 text-md">
            <input
              type="file"
              accept=".pdf,.jpg,.jpeg,.png"
              onChange={handleFileChange}
              required
            />
            {fileError && (
              <p className="break-keep text-sm text-red-500">{fileError}</p>
            )}
          </div>
        </figure>

        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">상호명</p>
          <Input
            name="franchiseName"
            value={formData2.franchiseName}
            className="border border-gray-300 px-4"
            required
            onChange={handleChange}
            maxLength={255}
          />
        </figure>

        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">카테고리</p>
          <Select
            styles={customStyles}
            name="category"
            value={category ? category : undefined}
            onChange={handleCategoryChange}
            options={categoryOptions}
            className="text-md"
            classNamePrefix="select"
            components={{
              IndicatorSeparator: () => null,
            }}
          />
        </figure>

        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">
            매장 전화번호
          </p>
          <Input
            name="telephoneNumber"
            value={formData2.telephoneNumber}
            className="border border-gray-300 px-4"
            required
            onChange={handleChange}
            maxLength={12}
          />
          {telephoneNumberError && (
            <p className="break-keep p-3 text-sm text-red-500">
              {telephoneNumberError}
            </p>
          )}
        </figure>

        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">주소</p>
        </figure>

        <div className="pb-20 pt-10">
          <Button
            label="가맹점주로 회원가입"
            variant="primary"
            type="submit"
            onClick={handleSubmit}
          />
        </div>
      </article>
    </main>
  );
};

export default UserSignUp4;
