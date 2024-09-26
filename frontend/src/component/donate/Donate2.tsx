import { Button } from '../../component/common';
import { CommonProps, CouponOption } from '../../types/donate';
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
];

const Donate2: React.FC<CommonProps> = ({
  onNext,
  donateInfo,
  setDonateInfo,
}) => {
  const { money, count2000, count4000, couponMessage } = donateInfo;

  const handleCountChange = (
    value: number,
    field: 'count2000' | 'count4000',
  ) => {
    setDonateInfo((prevInfo) => {
      const updatedInfo = {
        ...prevInfo,
        [field]: Math.max(prevInfo[field] + value, 0),
      };
      const newMoney =
        updatedInfo.count2000 * 2000 + updatedInfo.count4000 * 4000;

      return {
        ...updatedInfo,
        money: newMoney,
      };
    });
  };

  const handleCouponChange = (selectedOption: SingleValue<CouponOption>) => {
    if (selectedOption) {
      setDonateInfo((prevInfo) => ({
        ...prevInfo,
        couponMessage: selectedOption.label,
      }));
    }
  };

  return (
    <main className="px-10 py-5">
      <figure className="mb-14 flex flex-col gap-y-10">
        <p className="ml-3 text-sm font-semibold text-gray-600">보낼 금액</p>
        <p className="mt-2 text-center text-4xl font-bold">{`${money.toLocaleString()}원`}</p>
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
              value={
                couponMessages.find(
                  (option) => option.label === couponMessage,
                ) || null
              }
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
              onClick={() => handleCountChange(-1, 'count2000')}
            />
            <span className="mx-2 text-lg font-semibold">{count2000}</span>
            <Button
              label="＋"
              variant="text"
              onClick={() => handleCountChange(1, 'count2000')}
            />
          </div>
        </div>
        <div className="flex flex-col gap-y-3 px-20">
          <p className="font-semibold">4000원 권</p>
          <div className="flex justify-center gap-4 rounded-lg border px-6 py-4">
            <Button
              label="－"
              variant="text"
              onClick={() => handleCountChange(-1, 'count4000')}
            />
            <span className="mx-2 text-lg font-semibold">{count4000}</span>
            <Button
              label="＋"
              variant="text"
              onClick={() => handleCountChange(1, 'count4000')}
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
