import { useState, useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Swiper as SwiperCore } from 'swiper';
import 'swiper/css';
import { useAtom } from 'jotai';
import { CreditCard } from '../common';
import { creditCardAtom } from '../../atoms/userAtom';
import { userAtom } from '../../atoms/userAtom';

interface cardSelectProps {
  onCardSelect: (cardId: string) => void;
}

const CardSelect: React.FC<cardSelectProps> = ({ onCardSelect }) => {
  const [user, setUser] = useAtom(userAtom);
  const [cards, setCards] = useAtom(creditCardAtom);
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
      slidesPerView={1.5}
      spaceBetween={25}
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
