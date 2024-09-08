const Coupon: React.FC<CouponProps> = ({ storeName, cost, couponID, onClick }) => {

  return (
    <div className="relative w-80 h-48 flex flex-col justify-center items-center">
      {/* 쿠폰 */}
      <div className="relative w-full h-full bg-orange-500 flex flex-col justify-center p-6 shadow-lg">
        <div className="items-start justify-center flex-grow">
          <div className="text-white text-sm">{couponID}</div>
          <div className="text-white text-sm">{storeName}</div>
          <div className="text-white text-4xl font-bold mt-2">{cost}원</div>
        </div>
        <div className="text-xs text-white">발급일 23:59까지 사용 가능</div>

        {/* 다운로드 버튼 */}
        <button
          onClick={onClick}
          className="absolute -bottom-2 -right-4 w-24 h-14 bg-gray-300 rounded-full shadow-lg flex justify-center items-center"
        >
          {/* 구름 윗부분 */}
          <div className="absolute -top-4 w-12 h-11 bg-gray-300 rounded-full"></div>
          {/* 아래 방향 화살표 */}
          <div className="relative w-6 h-6 bg-transparent">
            <div className="absolute left-0 right-0 mx-auto w-4 h-4 border-b-4 border-r-4 border-white transform rotate-45"></div>
          </div>
        </button>
      </div>
    </div>
  );
};

export default Coupon;
