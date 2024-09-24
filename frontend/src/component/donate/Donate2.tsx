import { Button } from '../../component/common';
import { Donate2Props, CouponOption } from '../../types/donate';
import Select, { StylesConfig, GroupBase, SingleValue } from 'react-select';

const customStyles: StylesConfig<
  CouponOption,
  false,
  GroupBase<CouponOption>
> = {
  control: (provided) => ({
    ...provided,
    height: '3.5rem',
    borderRadius: '30px',
    border: 'none',
    boxShadow:
      '0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08)',
    padding: '0 1rem',
    fontSize: '1rem',
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

const couponMessages: CouponOption[] = [
  { value: '✨ 오늘도 즐거운 하루', label: '✨ 오늘도 즐거운 하루', id: 1 },
  { value: '🌟 행복한 하루 보내세요', label: '🌟 행복한 하루 보내세요', id: 2 },
  { value: 'for you 🎁', label: 'for you 🎁', id: 3 },
  { value: '화이팅 화이팅 화이팅 🥰', label: '화이팅 화이팅 화이팅 🥰', id: 4 },
  { value: '🥗 건강한 식사를 위하여', label: '🥗 건강한 식사를 위하여', id: 5 },
  {
    value: '오늘은 왠지 햄버거가 땡기는 날🍔🍟',
    label: '오늘은 왠지 햄버거가 땡기는 날🍔🍟',
    id: 6,
  },
];

const Donate2: React.FC<Donate2Props> = ({
  onNext,
  count2000,
  setCount2000,
  count4000,
  setCount4000,
  couponMessage,
  setCouponMessage,
  amount,
}) => {
  const handleCountChange = (
    value: number,
    setCount: React.Dispatch<React.SetStateAction<number>>,
  ) => {
    setCount((prev) => Math.max(prev + value, 0));
  };

  const handleCouponChange = (selectedOption: SingleValue<CouponOption>) => {
    if (selectedOption) {
      setCouponMessage(selectedOption.value);
    }
  };

  return (
    <main className="px-10 py-5">
      <figure className="mb-14 flex flex-col gap-y-10">
        <p className="ml-3 text-sm font-semibold text-gray-600">보낼 금액</p>
        <p className="mt-2 text-center text-4xl font-bold">{`${amount.toLocaleString()}원`}</p>
      </figure>

      <figure className="flex flex-col gap-y-10">
        <label className="flex flex-col gap-y-3">
          <span className="ml-3 font-semibold text-gray-600">
            쿠폰 발행 멘트 선택
          </span>
          <div className="mt-2 flex flex-col">
            <Select
              styles={customStyles}
              name="couponMessage"
              value={couponMessages.find(
                (option) => option.value === couponMessage,
              )}
              onChange={handleCouponChange}
              options={couponMessages}
              className="basic-single"
              classNamePrefix="select"
              components={{ IndicatorSeparator: () => null }}
            />
          </div>
        </label>

        <div className="flex flex-col gap-y-3 px-20">
          <p className="font-semibold">2000원 권</p>
          <div className="flex justify-center gap-4 rounded-lg border px-6 py-4">
            <Button
              label="－"
              variant="text"
              onClick={() => handleCountChange(-1, setCount2000)}
            />
            <span className="mx-2 text-lg font-semibold">{count2000}</span>
            <Button
              label="＋"
              variant="text"
              onClick={() => handleCountChange(1, setCount2000)}
            />
          </div>
        </div>
        <div className="flex flex-col gap-y-3 px-20">
          <p className="font-semibold">4000원 권</p>
          <div className="flex justify-center gap-4 rounded-lg border px-6 py-4">
            <Button
              label="－"
              variant="text"
              onClick={() => handleCountChange(-1, setCount4000)}
            />
            <span className="mx-2 text-lg font-semibold">{count4000}</span>
            <Button
              label="＋"
              variant="text"
              onClick={() => handleCountChange(1, setCount4000)}
            />
          </div>
        </div>
      </figure>

      <div className="mb-15 mt-10">
        <Button label="다음으로" variant="primary" onClick={onNext} />
      </div>
    </main>
  );
};

export default Donate2;
