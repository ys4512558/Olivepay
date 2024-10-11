import { useState } from 'react';
import CreditCardBack from './CreditCardBack';
import CreditCardFront from './CreditCardFront';

const CreditCard: React.FC<CreditCardProps> = ({
  cardNumber,
  cardOwner,
  cardName,
  isDefault,
  onClick,
}) => {
  const [isFlipped, setIsFlipped] = useState(false);

  const handleFlip = () => {
    setIsFlipped(!isFlipped);
  };

  return (
    <div className="relative h-44 w-64 cursor-pointer" onClick={handleFlip}>
      <div
        className="relative h-full w-full transition-transform duration-500 ease-in-out"
        style={{
          transform: isFlipped ? 'rotateY(180deg)' : 'rotateY(0deg)',
          transformStyle: 'preserve-3d',
        }}
      >
        {/* 앞면 */}
        <div
          className="absolute h-full w-full"
          style={{
            backfaceVisibility: 'hidden',
          }}
        >
          <CreditCardFront cardName={cardName} isDefault={isDefault} />
        </div>

        {/* 뒷면 */}
        <div
          className="absolute h-full w-full"
          style={{
            transform: 'rotateY(180deg)',
            backfaceVisibility: 'hidden',
          }}
        >
          <CreditCardBack
            cardNumber={cardNumber}
            cardOwner={cardOwner}
            cardName={cardName}
            onClick={onClick}
          />
        </div>
      </div>
    </div>
  );
};

export default CreditCard;
