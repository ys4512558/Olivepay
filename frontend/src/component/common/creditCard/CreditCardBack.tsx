import React from 'react';

type CreditCardProps = {
  bgColor: string;
  cardName?: string;
  cardNumber?: string;
  cardOwner?: string;
};

const CreditCardBack: React.FC<CreditCardProps> = ({
  cardNumber,
  cardOwner,
  bgColor,
}) => {
  return (
    <div
      className="relative flex h-44 w-72 cursor-pointer flex-col justify-end rounded-lg p-5 shadow-md"
      style={{
        background: bgColor,
      }}
    >
      {/* 카드 칩 위치 */}
      <div className="absolute left-4 top-20 h-6 w-10">
        <img src="cardChip.svg" alt="Chip" />
      </div>

      {/* 카드 정보 */}
      <div className="text-sm text-white">
        <div>{cardNumber}</div>
        <div>{cardOwner}</div>
      </div>
      <div className="absolute bottom-4 right-4 w-12">
        <img src="cardLogo.svg" alt="Logo" />
      </div>
    </div>
  );
};

export default CreditCardBack;
