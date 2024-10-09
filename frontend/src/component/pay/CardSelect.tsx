import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Swiper as SwiperCore } from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';
import { Pagination } from 'swiper/modules';
import { useAtom } from 'jotai';
import { CreditCard, Loader } from '../common';
import { creditCardAtom, canPayAtom } from '../../atoms/userAtom';
import { userAtom } from '../../atoms/userAtom';
import { getCardsInfo } from '../../api/cardApi';
import { useQuery } from '@tanstack/react-query';
import { useSnackbar } from 'notistack';

const CardSelect: React.FC<cardSelectProps> = ({
  onCardSelect,
  finalPayment,
}) => {
  const navigate = useNavigate();
  const { enqueueSnackbar } = useSnackbar();
  const [user] = useAtom(userAtom);
  const [, setCanPay] = useAtom(canPayAtom);
  const [cards, setCards] = useAtom(creditCardAtom);
  const [activeIndex, setActiveIndex] = useState<number>(0);
  const payCards = cards.filter((card) => card.cardCompany !== '꿈나무카드');

  const { data, error, isLoading, isSuccess } = useQuery({
    queryKey: ['card'],
    queryFn: getCardsInfo,
  });

  useEffect(() => {
    if (isSuccess && data) {
      setCards(data);
      if (finalPayment > 0 && data.length === 0) {
        setCanPay(false);
        enqueueSnackbar('등록된 카드가 없습니다. 등록 후 다시 시도해주세요.', {
          variant: 'info',
        });
      }
    }
  }, [data, isSuccess, setCards, setCanPay, enqueueSnackbar, finalPayment]);

  useEffect(() => {
    if (activeIndex !== null && payCards.length > 0) {
      onCardSelect(payCards[activeIndex].cardId);
    }
  }, [activeIndex, payCards, onCardSelect]);

  const handleSlideChange = useCallback(
    (swiper: SwiperCore) => {
      setActiveIndex(swiper.activeIndex);
      onCardSelect(payCards[swiper.activeIndex].cardId);
    },
    [payCards, onCardSelect],
  );

  if (isLoading) return <Loader />;

  if (error) return <div>카드 조회 실패</div>;

  return (
    <Swiper
      pagination={true}
      modules={[Pagination]}
      grabCursor={true}
      onSlideChange={handleSlideChange}
      style={{ width: '100%' }}
    >
      {payCards.length === 0 ? (
        <SwiperSlide
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}
        >
          <div
            className="flex h-44 w-64 cursor-pointer items-center justify-center rounded-lg border-2 border-dashed border-BASE"
            onClick={() => navigate('/card')}
          >
            <div className="text-center text-DARKBASE">
              <span className="text-4xl">+</span>
              <p className="text-base">카드 추가</p>
            </div>
          </div>
        </SwiperSlide>
      ) : (
        payCards.map((card) => (
          <SwiperSlide
            key={card.cardId}
            style={{
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
            }}
          >
            <CreditCard
              cardName={card.cardCompany + ' ' + card.cardId}
              cardNumber={card.realCardNumber}
              cardOwner={user.name}
              isDefault={card.isDefault}
            />
          </SwiperSlide>
        ))
      )}
    </Swiper>
  );
};

export default CardSelect;
