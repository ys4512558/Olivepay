import React from 'react';

type CreditCardProps = {
  bgColor: string;
  cardName?: string;
  cardNumber?: string;
  cardOwner?: string;
};

const CreditCardFront: React.FC<CreditCardProps> = ({ cardName, bgColor }) => {
  return (
    <div
      className="relative flex h-44 w-72 cursor-pointer flex-col justify-center rounded-lg shadow-md"
      style={{
        background: bgColor,
        backfaceVisibility: 'hidden',
        WebkitBackfaceVisibility: 'hidden',
      }}
    >
      <div className="absolute left-4 top-20 h-6 w-10">
        <img src="cardChip.svg" alt="Chip" />
      </div>
      {/* 카드 이름 */}
      <div className="flex justify-center">
        <div className="text-lg font-bold text-white">{cardName}</div>
      </div>
      <div className="absolute bottom-4 right-4 w-12">
        <img src="cardLogo.svg" alt="Logo" />
      </div>
    </div>
  );
};

export default CreditCardFront;
