import clsx from 'clsx';
import getCardBackground from '../../../utils/cardColors';

const CreditCardBack: React.FC<CreditCardProps> = ({
  cardNumber,
  cardOwner,
  cardName,
}) => {
  const cardBackgroundClass = getCardBackground(cardName);

  return (
    <div
      className={clsx(
        'relative flex h-44 w-full cursor-pointer justify-center rounded-lg shadow-md',
        `bg-${cardBackgroundClass}`,
      )}
    >
      {/* 카드 칩 위치 */}
      <div className="absolute left-4 top-20 h-6 w-10">
        <img src="/cardChip.svg" alt="Chip" />
      </div>

      {/* 카드 정보 */}
      <div className="absolute bottom-5 left-5 text-sm text-white">
        <div>{cardNumber}</div>
        <div>{cardOwner}</div>
      </div>
      <div className="absolute bottom-4 right-4 w-12">
        <img src="/cardLogo.svg" alt="Logo" />
      </div>
    </div>
  );
};

export default CreditCardBack;
