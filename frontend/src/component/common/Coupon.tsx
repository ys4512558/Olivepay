const Coupon: React.FC<CouponProps> = ({
  storeName,
  cost,
  couponID,
  onClick,
}) => {
  return (
    <main className="relative flex h-48 w-80 flex-col items-center justify-center">
      {/* 쿠폰 */}
      <section className="bg-TERTIARY relative flex h-full w-full flex-col justify-center p-6 shadow-lg">
        <article className="flex-grow items-start justify-center">
          <div className="text-sm text-white">{couponID}</div>
          <div className="text-sm text-white">{storeName}</div>
          <div className="mt-2 text-4xl font-bold text-white">{cost}원</div>
        </article>
        <div className="text-xs text-white">발급일 23:59까지 사용 가능</div>

        {/* 다운로드 버튼 */}
        <button
          onClick={onClick}
          className="absolute -bottom-2 -right-4 flex h-14 w-24 items-center justify-center rounded-full bg-gray-300 shadow-lg"
        >
          {/* 구름 윗부분 */}
          <div className="absolute -top-4 h-11 w-12 rounded-full bg-gray-300"></div>
          {/* 아래 방향 화살표 */}
          <div className="relative h-6 w-6 bg-transparent">
            <div className="absolute left-0 right-0 mx-auto h-4 w-4 rotate-45 transform border-b-4 border-r-4 border-white"></div>
          </div>
        </button>
      </section>
    </main>
  );
};

export default Coupon;
