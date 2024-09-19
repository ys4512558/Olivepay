import { useState } from 'react';
import { Input, Layout, Button } from '../common';
import {
  numericRegex,
  certificateRegistrationNumberRegex,
  seoulRegex,
  otherRegionsRegex,
  formatTelephoneNumber,
} from '../../utils/validators';

const UserSignUp4: React.FC<UserSignUpProps> = ({
  formData2,
  handleFormDataChange,
  handleSubmit,
}) => {
  const [registrationNumberError, setRegistrationNumberError] = useState('');
  const [telephoneNumberError, setTelephoneNumberError] = useState('');
  const [fileError, setFileError] = useState('');

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
    <Layout>
      <main>
        <article className="flex flex-col gap-y-6 p-10">
          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">사업자등록번호</p>
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

          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">사업자등록증 첨부</p>
            <div className="ps-3 pt-3">
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

          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">상호명</p>
            <Input
              name="franchiseName"
              value={formData2.franchiseName}
              className="border border-gray-300 px-4"
              required
              onChange={handleChange}
              maxLength={255}
            />
          </figure>

          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">카테고리</p>
            <select
              name="category"
              value={formData2.category}
              className="h-14 rounded-full border border-gray-300 px-4 py-2 shadow-lg"
              onChange={handleChange}
              required
            >
              <option value="">카테고리 선택</option>
              <option value="한식">한식</option>
              <option value="양식">양식</option>
              <option value="중식">중식</option>
              <option value="일식">일식</option>
              <option value="패스트푸드">패스트푸드</option>
              <option value="제과점">제과점</option>
              <option value="일반대중음식">일반대중음식</option>
              <option value="할인점/슈퍼마켓">할인점/슈퍼마켓</option>
              <option value="기타">기타</option>
            </select>
          </figure>

          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">매장 전화번호</p>
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

          <figure className="flex flex-col gap-y-1">
            <p className="ms-3 text-gray-600">주소</p>
          </figure>

          <div className="py-10">
            <Button
              label="가맹점주로 회원가입"
              variant="primary"
              type="submit"
              onClick={handleSubmit}
            />
          </div>
        </article>
      </main>
    </Layout>
  );
};

export default UserSignUp4;
