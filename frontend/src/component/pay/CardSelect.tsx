import { useState, useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Swiper as SwiperCore } from 'swiper';
import 'swiper/css';
import { useAtom } from 'jotai';
import { CreditCard } from '../common';
import { creditCardAtom } from '../../atoms/userAtom';
import { userAtom } from '../../atoms/userAtom';

const CardSelect: React.FC<cardSelectProps> = ({ onCardSelect }) => {
  const [user] = useAtom(userAtom);
  const [cards] = useAtom(creditCardAtom);
  const [activeIndex, setActiveIndex] = useState<number>(0);
  const payCards = cards.filter((card) => card.cardCompany !== '꿈나무');

  useEffect(() => {
    if (activeIndex !== null && payCards.length > 0) {
      onCardSelect(payCards[activeIndex].cardId);
    }
  }, [activeIndex, payCards, onCardSelect]);

  const handleSlideChange = (swiper: SwiperCore) => {
    setActiveIndex(swiper.activeIndex);
    onCardSelect(payCards[swiper.activeIndex].cardId);
  };

  return (
    <Swiper
      slidesPerView={1.3}
      centeredSlides={true}
      grabCursor={true}
      onSlideChange={handleSlideChange}
    >
      {payCards.map((card) => {
        return (
          <SwiperSlide key={card.cardId}>
            <CreditCard
              cardName={card.cardCompany + ' ' + card.cardId}
              cardNumber={card.realCardNumber}
              cardOwner={user.name}
              isDefault={card.isDefault}
            />
          </SwiperSlide>
        );
      })}
    </Swiper>
  );
};

export default CardSelect;
