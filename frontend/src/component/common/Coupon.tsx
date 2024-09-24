const Coupon: React.FC<CouponProps> = ({
  storeName,
  cost,
  couponID,
  onClick,
  forFranchiser = false,
  count,
}) => {
  return (
    <div className="relative flex h-48 w-80 flex-col items-center justify-center">
      {/* 쿠폰 */}
      <div className="relative flex h-full w-full flex-col justify-center rounded-xl bg-TERTIARY p-6 shadow-lg">
        <div className="flex-grow items-start justify-center">
          <p className="text-sm text-white">{couponID}</p>
          <p className="text-sm text-white">{storeName}</p>
          <p className="mt-4 text-4xl font-bold text-white">
            {cost?.toLocaleString()}원
          </p>
        </div>
        {!forFranchiser && (
          <p className="text-xs text-white">발급일 23:59까지 사용 가능</p>
        )}
        {forFranchiser && (
          <div className="relative">
            <div className="absolute -bottom-10 -right-12 flex size-24 items-center justify-center rounded-full bg-SECONDARY text-xl text-white">
              <span className="mr-1 pb-[2px] text-2xl font-semibold">
                {count}
              </span>
              장
            </div>
          </div>
        )}
        {onClick && (
          <button
            onClick={onClick}
            className="absolute -bottom-2 -right-4 flex h-14 w-24 items-center justify-center rounded-full bg-gray-300 shadow-lg"
          >
            <div className="absolute -top-4 h-11 w-12 rounded-full bg-gray-300"></div>
            <div className="relative h-6 w-6 bg-transparent">
              <div className="absolute left-0 right-0 mx-auto h-4 w-4 rotate-45 transform border-b-4 border-r-4 border-white"></div>
            </div>
          </button>
        )}
      </div>
    </div>
  );
};

export default Coupon;
