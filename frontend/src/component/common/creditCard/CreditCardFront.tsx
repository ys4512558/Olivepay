import clsx from 'clsx';
import getCardBackground from '../../../utils/cardColors';
import { StarIcon } from '@heroicons/react/24/solid';

const CreditCardFront: React.FC<CreditCardProps> = ({
  cardName,
  isDefault,
}) => {
  const cardBackgroundClass = getCardBackground(cardName);

  return (
    <div
      className={clsx(
        'relative flex h-44 w-full cursor-pointer flex-col justify-center rounded-lg shadow-md',
        `bg-${cardBackgroundClass}`,
      )}
      style={{
        backfaceVisibility: 'hidden',
        WebkitBackfaceVisibility: 'hidden',
      }}
    >
      {isDefault && (
        <div className="absolute right-6 top-6">
          <StarIcon
            className="size-5 text-YELLOW"
            stroke="gray"
            title="기본 카드"
          />
        </div>
      )}
      {/* 카드 이름 */}
      <div className="flex justify-center">
        <div className="text-lg font-bold text-white">{cardName}</div>
      </div>
      <div className="absolute bottom-4 right-4 w-12">
        <img src="/image/cardLogo.svg" alt="Logo" />
      </div>
    </div>
  );
};

export default CreditCardFront;
