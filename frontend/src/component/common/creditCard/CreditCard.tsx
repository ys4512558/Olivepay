import React, { useState } from 'react';
import CreditCardBack from './CreditCardBack';
import CreditCardFront from './CreditCardFront';

const CreditCard: React.FC = () => {
  // 카드의 앞면/뒷면 상태 관리
  const [isFlipped, setIsFlipped] = useState(false);

  // 클릭에 따라 카드 회전
  const handleFlip = () => {
    setIsFlipped(!isFlipped);
  };

  return (
    <div className="relative h-44 w-72 cursor-pointer" onClick={handleFlip}>
      <div
        className="relative h-full w-full transition-transform duration-500 ease-in-out"
        style={{
          transform: isFlipped ? 'rotateY(180deg)' : 'rotateY(0deg)',
          transformStyle: 'preserve-3d', // 자식 요소와 부모 요소를 함께 3D 공간에서 회전시킴
        }}
      >
        {/* 앞면 */}
        <div
          style={{
            backfaceVisibility: 'hidden',
            position: 'absolute',
            width: '100%',
            height: '100%',
          }}
        >
          <CreditCardFront
            cardName="꿈나무카드"
            bgColor="linear-gradient(45deg, #229b41, #32c93c, #a5ce14)"
          />
        </div>

        {/* 뒷면 */}
        <div
          style={{
            transform: 'rotateY(180deg)',
            backfaceVisibility: 'hidden',
            position: 'absolute',
            width: '100%',
            height: '100%',
          }}
        >
          <CreditCardBack
            cardNumber="0000 1111 2222 3333"
            cardOwner="홍길동"
            bgColor="linear-gradient(45deg, #229b41, #32c93c, #a5ce14)"
          />
        </div>
      </div>
    </div>
  );
};

export default CreditCard;
