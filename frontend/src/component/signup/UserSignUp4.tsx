import { useState, useEffect } from 'react';
import Select, { StylesConfig, GroupBase, SingleValue } from 'react-select';
import { Input, Button } from '../common';
import {
  numericRegex,
  certificateRegistrationNumberRegex,
  formatTelephoneNumber
} from '../../utils/validators';
import { franchiseCategory } from '../../types/franchise';
import { getFranchiseCategoryEmoji } from '../../utils/category';
import PostCodeSearch from './PostCodeSearch';
import { useSnackbar } from 'notistack';

const categoryOptions = (Object.keys(franchiseCategory) as Array<keyof typeof franchiseCategory>).map((key) => ({
  value: key,
  label: `${getFranchiseCategoryEmoji(franchiseCategory[key])} ${franchiseCategory[key]}`, // 한글 레이블
}));

const customStyles: StylesConfig<
  (typeof categoryOptions)[0],
  false,
  GroupBase<(typeof categoryOptions)[0]>
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

const UserSignUp4: React.FC<UserSignUpProps> = ({
  formData2,
  handleFormDataChange,
  handleSubmit,
}) => {
  const { enqueueSnackbar } = useSnackbar();
  const [registrationNumberError, setRegistrationNumberError] = useState('');
  const [telephoneNumberError, setTelephoneNumberError] = useState('');
  const [fileError, setFileError] = useState('');
  const [category, setCategory] = useState<(typeof categoryOptions)[0] | null>(
    categoryOptions[0],
  );
  const [mainAddress, setMainAddress] = useState('');
  const [detailAddress, setDetailAddress] = useState('');

  useEffect(() => {
    handleFormDataChange('category', categoryOptions[0].value, 'formData2');
  }, []);

  useEffect(() => {
    const fullAddress = `${mainAddress} ${detailAddress}`.trim();
    handleFormDataChange('address', fullAddress, 'formData2');
  }, [mainAddress, detailAddress]);


  const handleCategoryChange = (
    selectedOption: SingleValue<(typeof categoryOptions)[0]>,
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
    setTelephoneNumberError('');

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
    } else if (name === 'registrationNumber') {
      if (!certificateRegistrationNumberRegex.test(value)) {
        setRegistrationNumberError('사업자등록번호는 10자리 숫자여야 합니다.');
      } else {
        setRegistrationNumberError('');
      }
      handleFormDataChange(name, value, 'formData2');
    }
  };

  const handleTelephoneBlur = () => {
    const formattedPhone = formatTelephoneNumber(formData2.telephoneNumber);
    handleFormDataChange('telephoneNumber', formattedPhone, 'formData2');
  };

  const handleTelephoneChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = e.target;
    setTelephoneNumberError('');
    if (!numericRegex.test(value)) {
      setTelephoneNumberError('숫자만 입력 가능합니다.');
      return;
    }
    handleFormDataChange('telephoneNumber', value, 'formData2');
  };

  const handleAddressSelect = async (mainAddress: string) => {
    try {
      setMainAddress(mainAddress);
      const response = await fetch(
        `https://dapi.kakao.com/v2/local/search/address.json?query=${mainAddress}`,
        {
          headers: { Authorization: `KakaoAK ${import.meta.env.VITE_REST_API_KEY}` },
        }
      );
      const data = await response.json();
      if (!data.documents || !Array.isArray(data.documents) || data.documents.length === 0) {
        console.error("No result found or data is invalid.");
        return;
      }
      const { x: longitude, y: latitude } = data.documents[0];
      handleFormDataChange('latitude', latitude, 'formData2');
      handleFormDataChange('longitude', longitude, 'formData2');
    } catch (error: unknown) {
      if (error instanceof Error) {
        if ('status' in error && 'response' in error) {
          const httpError = error as { status: number; response: Response };
          if (httpError.status === 400) {
            const errorMessage = await httpError.response.json();
            enqueueSnackbar(
              `${errorMessage?.data?.data || '알 수 없는 오류가 발생했습니다.'}`,
              { variant: 'error' }
            );
          }
        } else {
          enqueueSnackbar(`오류 발생: ${error.message}`, { variant: 'error' });
        }
      }
    }
  };
  
  const handleDetailAddressChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDetailAddress(e.target.value);
  };
  return (
    <main>
      <article className="flex flex-col gap-y-6 p-5">
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
            placeholder="1234567890"
          />
          <div className="min-h-5">
            {registrationNumberError && (
              <p className="mt-1 ps-3 text-sm text-red-500">
                {registrationNumberError}
              </p>
            )}
          </div>
        </figure>

        <figure className="flex flex-col gap-y-2 pb-5">
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
            <div className="min-h-5">
              {fileError && (
                <p className="break-keep text-sm text-red-500">{fileError}</p>
              )}
            </div>
          </div>
        </figure>

        <figure className="flex flex-col gap-y-2 pb-5">
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

        <figure className="flex flex-col gap-y-2 pb-5">
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
            onChange={handleTelephoneChange}
            onBlur={handleTelephoneBlur} 
            maxLength={10}
            placeholder="숫자만 입력하세요"
          />
          <div className="min-h-5">
            {telephoneNumberError && (
              <p className="mt-1 ps-3 text-sm text-red-500">
                {telephoneNumberError}
              </p>
            )}
          </div>
        </figure>

        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">주소</p>
          <div className="flex items-center gap-x-2">
            <Input
              type="text"
              placeholder="주소"
              value={mainAddress}
              readOnly
              className="flex-grow"
            />
            <PostCodeSearch onAddressSelect={handleAddressSelect} />
          </div>
          <Input
            type="text"
            placeholder="상세주소"
            value={detailAddress}
            className="mt-2"
            onChange={handleDetailAddressChange}
          />
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
