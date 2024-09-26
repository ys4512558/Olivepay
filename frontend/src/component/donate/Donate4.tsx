import { Button } from '../../component/common';
import { CommonProps } from '../../types/donate';

const Donate4: React.FC<CommonProps> = ({ onNext, donateInfo }) => {
  const { money, accountNumber, count2000, count4000, couponMessage } =
    donateInfo;

  return (
    <main className="flex flex-col gap-y-2 px-10 py-4">
      <figure className="mb-14 flex flex-col gap-y-5">
        <p className="ml-3 text-md font-bold text-gray-600">보낼 금액</p>
        <p className="mt-2 text-center text-3xl font-bold">
          {money !== undefined && money !== null
            ? `${money.toLocaleString()}원`
            : '0원'}
        </p>{' '}
      </figure>

      <div className="mb-4 rounded-3xl bg-LIGHTBASE px-5 py-1 text-md font-semibold">
        <p>내 계좌 : {accountNumber} </p>
      </div>

      <div className="mt-3 flex items-center">
        <div className="flex w-full flex-col items-start">
          <p className="mb-2 text-md font-bold">쿠폰 메세지</p>
          <p className="mt-3 w-full break-keep rounded-lg border p-4 text-left text-md">
            {couponMessage}
          </p>
        </div>
      </div>

      <p className="mb-6 mt-6 font-bold">쿠폰 발행 구성</p>
      <div className="ml-2 flex flex-col items-center gap-4">
        <div className="flex w-full max-w-md items-center gap-x-10">
          <label className="text-md font-semibold">2,000원권</label>
          <p className="rounded-lg border px-10 py-4 text-center text-md">
            {count2000}장
          </p>
        </div>
        <div className="flex w-full max-w-md items-center gap-x-10">
          <label className="text-md font-semibold">4,000원권</label>
          <p className="rounded-lg border px-10 py-4 text-center text-md">
            {count4000}장
          </p>
        </div>
      </div>

      <Button
        label="완료"
        variant="primary"
        onClick={onNext}
        className="mb-10 mt-6"
      />
    </main>
  );
};

export default Donate4;
