import React, { useState } from 'react';
import clsx from 'clsx';

// Utility function for tw strings
const tw = (strings: TemplateStringsArray): string => {
  return strings.join('');
};

// 카드 스타일
const CARD_STYLES = {
  flipCard: tw`relative h-44 w-72 cursor-pointer`,
  flipCardInner: tw`relative h-full w-full transition-transform duration-500 ease-in-out`,
  flipCardFront: tw`backface-hidden absolute flex h-full w-full flex-col justify-center rounded-lg shadow-md`,
  flipCardBack: tw`backface-hidden transform-rotate-y-180 absolute flex h-full w-full flex-col justify-end rounded-lg p-5 shadow-md`,
  cardInfo: tw`flex justify-center`,
  cardName: tw`text-lg font-bold text-white`,
  cardDetails: tw`text-sm text-white`, // 카드 번호, 소유자 정보
  cardChip: tw`absolute left-4 top-20 h-6 w-10`,
  cardLogo: tw`absolute bottom-4 right-4 w-12`,
};

// 인라인 스타일링을 위한 스타일 객체
const inlineStyles = {
  flipCard: {
    perspective: '1000px',
  } as React.CSSProperties,
  flipCardInner: (isFlipped: boolean): React.CSSProperties => ({
    transform: isFlipped ? 'rotateY(180deg)' : 'rotateY(0deg)',
    transformStyle: 'preserve-3d',
    transition: 'transform 0.8s',
  }),
  cardSide: {
    backfaceVisibility: 'hidden',
    WebkitBackfaceVisibility: 'hidden',
  } as React.CSSProperties,
  cardBack: {
    transform: 'rotateY(180deg)',
  } as React.CSSProperties,
};

// CreditCard 앞면
type CreditCardFrontProps = {
  cardName: string;
  bgColor: string;
};

const CreditCardFront: React.FC<CreditCardFrontProps> = ({
  cardName,
  bgColor,
}) => {
  return (
    <div
      className={clsx(CARD_STYLES.flipCardFront)}
      style={{ background: bgColor, ...inlineStyles.cardSide }}
    >
      <div className={clsx(CARD_STYLES.cardInfo)}>
        <div className={clsx(CARD_STYLES.cardName)}>{cardName}</div>
      </div>
    </div>
  );
};

// CreditCardBack 뒷면
type CreditCardBackProps = {
  cardNumber: string;
  cardOwner: string;
  bgColor: string;
};

const CreditCardBack: React.FC<CreditCardBackProps> = ({
  cardNumber,
  cardOwner,
  bgColor,
}) => {
  return (
    <div
      className={clsx(CARD_STYLES.flipCardBack)}
      style={{
        background: bgColor,
        ...inlineStyles.cardSide,
        ...inlineStyles.cardBack,
      }}
    >
      {/* 카드 칩 SVG */}
      <svg
        version="1.1"
        className={clsx(CARD_STYLES.cardChip)}
        id="Layer_1"
        xmlns="http://www.w3.org/2000/svg"
        x="0px"
        y="0px"
        width="30px"
        height="30px"
        viewBox="0 0 50 50"
        xmlSpace="preserve" // xml:space는 xmlSpace로 변경
      >
        <image
          id="image0"
          width="50"
          height="50"
          x="0"
          y="0"
          xlinkHref="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAMAAAAp4XiDAAAABGdBTUEAALGPC/xhBQAAACBjSFJN
      AAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAB6VBMVEUAAACNcTiVeUKVeUOY
      fEaafEeUeUSYfEWZfEaykleyklaXe0SWekSZZjOYfEWYe0WXfUWXe0WcgEicfkiXe0SVekSXekSW
      ekKYe0a9nF67m12ZfUWUeEaXfESVekOdgEmVeUWWekSniU+VeUKVeUOrjFKYfEWliE6WeESZe0GS
      e0WYfES7ml2Xe0WXeESUeEOWfEWcf0eWfESXe0SXfEWYekSVeUKXfEWxklawkVaZfEWWekOUekOW
      ekSYfESZe0eXekWYfEWZe0WZe0eVeUSWeETAnmDCoWLJpmbxy4P1zoXwyoLIpWbjvXjivnjgu3bf
      u3beunWvkFWxkle/nmDivXiWekTnwXvkwHrCoWOuj1SXe0TEo2TDo2PlwHratnKZfEbQrWvPrWua
      fUfbt3PJp2agg0v0zYX0zYSfgkvKp2frxX7mwHrlv3rsxn/yzIPgvHfduXWXe0XuyIDzzISsjVO1
      lVm0lFitjVPzzIPqxX7duna0lVncuHTLqGjvyIHeuXXxyYGZfUayk1iyk1e2lln1zYTEomO2llrb
      tnOafkjFpGSbfkfZtXLhvHfkv3nqxH3mwXujhU3KqWizlFilh06khk2fgkqsjlPHpWXJp2erjVOh
      g0yWe0SliE+XekShhEvAn2D///+gx8TWAAAARnRSTlMACVCTtsRl7Pv7+vxkBab7pZv5+ZlL/UnU
      /f3SJCVe+Fx39naA9/75XSMh0/3SSkia+pil/KRj7Pr662JPkrbP7OLQ0JFOijI1MwAAAAFiS0dE
      orDd34wAAAAJcEhZcwAACxMAAAsTAQCanBgAAAAHdElNRQfnAg0IDx2lsiuJAAACLElEQVRIx2Ng
      GAXkAUYmZhZWPICFmYkRVQcbOwenmzse4MbFzc6DpIGXj8PD04sA8PbhF+CFaxEU8iWkAQT8hEVg
      OkTF/InR4eUVICYO1SIhCRMLDAoKDvFDVhUaEhwUFAjjSUlDdMiEhcOEItzdI6OiYxA6YqODIt3d
      I2DcuDBZsBY5eVTr4xMSYcyk5BRUOXkFsBZFJTQnp6alQxgZmVloUkrKYC0qqmji2WE5EEZuWB6a
      lKoKdi35YQUQRkFYPpFaCouKIYzi6EDitJSUlsGY5RWVRGjJLyxNy4ZxqtIqqvOxaVELQwZFZdkI
      JVU1RSiSalAt6rUwUBdWG1CP6pT6gNqwOrgCdQyHNYR5YQFhDXj8MiK1IAeyN6aORiyBjByVTc0F
      qBoKWpqwRCVSgilOaY2OaUPw29qjOzqLvTAchpos47u6EZyYnngUSRwpuTe6D+6qaFQdOPNLRzOM
      1dzhRZyW+CZouHk3dWLXglFcFIflQhj9YWjJGlZcaKAVSvjyPrRQ0oQVKDAQHlYFYUwIm4gqExGm
      BSkutaVQJeomwViTJqPK6OhCy2Q9sQBk8cY0DxjTJw0lAQWK6cOKfgNhpKK7ZMpUeF3jPa28BCET
      amiEqJKM+X1gxvWXpoUjVIVPnwErw71nmpgiqiQGBjNzbgs3j1nus+fMndc+Cwm0T52/oNR9lsdC
      S24ra7Tq1cbWjpXV3sHRCb1idXZ0sGdltXNxRateRwHRAACYHutzk/2I5QAAACV0RVh0ZGF0ZTpj
      cmVhdGUAMjAyMy0wMi0xM1QwODoxNToyOSswMDowMEUnN7UAAAAldEVYdGRhdGU6bW9kaWZ5ADIw
      MjMtMDItMTNUMDg6MTU6MjkrMDA6MDA0eo8JAAAAKHRFWHRkYXRlOnRpbWVzdGFtcAAyMDIzLTAy
      LTEzVDA4OjE1OjI5KzAwOjAwY2+u1gAAAABJRU5ErkJggg=="
        ></image>
      </svg>

      {/* 카드 번호와 소유주 정보 */}
      <div className={clsx(CARD_STYLES.cardDetails)}>
        <div>{cardNumber}</div>
        <div>{cardOwner}</div>
      </div>

      {/* 카드 로고 SVG */}
      <svg
        className={clsx(CARD_STYLES.cardLogo)}
        xmlns="http://www.w3.org/2000/svg"
        x="0px"
        y="0px"
        width="36"
        height="36"
        viewBox="0 0 48 48"
      >
        <path
          fill="#ff9800"
          d="M32 10A14 14 0 1 0 32 38A14 14 0 1 0 32 10Z"
        ></path>
        <path
          fill="#d50000"
          d="M16 10A14 14 0 1 0 16 38A14 14 0 1 0 16 10Z"
        ></path>
        <path
          fill="#ff3d00"
          d="M18,24c0,4.755,2.376,8.95,6,11.48c3.624-2.53,6-6.725,6-11.48s-2.376-8.95-6-11.48 C20.376,15.05,18,19.245,18,24z"
        ></path>
      </svg>
    </div>
  );
};

// FlipCard 컨테이너 컴포넌트 (앞면/뒷면 결합)
type FlipCardProps = {
  cardName: string;
  cardNumber: string;
  cardOwner: string;

  frontBgColor: string;
  backBgColor: string;
};

const FlipCard: React.FC<FlipCardProps> = ({
  cardName,
  cardNumber,
  cardOwner,

  frontBgColor,
  backBgColor,
}) => {
  const [isFlipped, setIsFlipped] = useState(false);

  return (
    <div
      className={clsx(CARD_STYLES.flipCard)}
      style={inlineStyles.flipCard}
      onMouseEnter={() => setIsFlipped(true)}
      onMouseLeave={() => setIsFlipped(false)}
    >
      <div
        className={clsx(CARD_STYLES.flipCardInner)}
        style={inlineStyles.flipCardInner(isFlipped)}
      >
        {/* 앞면 */}
        <CreditCardFront cardName={cardName} bgColor={frontBgColor} />
        {/* 뒷면 */}
        <CreditCardBack
          cardNumber={cardNumber}
          cardOwner={cardOwner}
          bgColor={backBgColor}
        />
      </div>
    </div>
  );
};

export default FlipCard;
