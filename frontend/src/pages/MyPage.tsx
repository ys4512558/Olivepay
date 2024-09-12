import { useState } from 'react';
import { useAtom } from 'jotai';
import { useQueries } from '@tanstack/react-query';
import { userAtom } from '../atoms';

import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/css';

import {
  ChatBubbleLeftIcon,
  Cog6ToothIcon,
  HandThumbUpIcon,
  PencilSquareIcon,
  CreditCardIcon,
  BuildingStorefrontIcon,
  InboxArrowDownIcon,
} from '@heroicons/react/24/solid';

import {
  CreditCard,
  Layout,
  NavigateBox,
  Loader,
  Button,
  Modal,
} from '../component/common';

import { NicknameChange, PasswordChange } from '../component/user';
import { UserInfo } from '../component/user';
import { creditCardAtom } from '../atoms/userAtom';
import { getUsersInfo } from '../api/userApi';
import { getCardsInfo } from '../api/cardApi';

const MyPage = () => {
  const [user, setUser] = useAtom(userAtom);
  const [cards, setCards] = useAtom(creditCardAtom);

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState<
    'nickname' | 'password' | null
  >(null);

  const openModal = (contentType: 'nickname' | 'password') => {
    setModalContent(contentType);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setModalContent(null);
  };

  // const queries = useQueries({
  //   queries: [
  //     {
  //       queryKey: ['user'],
  //       queryFn: getUsersInfo,
  //     },
  //     {
  //       queryKey: ['card'],
  //       queryFn: getCardsInfo,
  //     },
  //   ],
  // });

  // const [
  //   { data: userData, error: userError, isLoading: userLoading },
  //   { data: cardData, error: cardError, isLoading: cardLoading },
  // ] = queries;

  // if (userData && cardData) {
  //   setUser(userData);
  //   setCards(cardData);
  // }

  // if (userLoading || cardLoading) return <Loader />;

  // if (userError || cardError) return <div>에러</div>;

  const handleAddCard = () => {
    console.log('나중에 카드 등록 페이지로 이동');
  };

  const handleLogout = () => {
    localStorage.clear();
  };

  return (
    <Layout>
      <main>
        <section className="bg-LIGHTBASE bg-opacity-50 pb-8 pt-8 shadow-md">
          <div className="mb-4 text-end">
            <Button variant="text" label="로그아웃" onClick={handleLogout} />
          </div>
          <div className="flex gap-2 px-4">
            <UserInfo user={user} className="h-42 flex-grow-[3] bg-white" />
            <div className="flex flex-grow flex-col gap-2">
              <NavigateBox
                className="h-20 bg-white"
                path="/mypage/nickname/edit"
                icon={<PencilSquareIcon className="size-6 text-PRIMARY" />}
                onClick={() => openModal('nickname')}
                text="닉네임"
              />
              <NavigateBox
                className="h-20 bg-white"
                path="/mypage/password/edit"
                icon={<Cog6ToothIcon className="size-6 text-PRIMARY" />}
                onClick={() => openModal('password')}
                text="비밀번호"
              />
            </div>
          </div>
        </section>
        <section className="my-8">
          <h2 className="my-4 text-center text-xl font-bold text-DARKBASE">
            보유 카드
          </h2>
          <Swiper slidesPerView={1.5} spaceBetween={25} centeredSlides={true}>
            {cards.map((card) => {
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
            <SwiperSlide>
              <div
                className="flex h-44 w-full cursor-pointer items-center justify-center rounded-lg border-2 border-dashed border-BASE"
                onClick={handleAddCard}
              >
                <div className="text-center text-DARKBASE">
                  <span className="text-4xl">+</span>
                  <p>카드 추가</p>
                </div>
              </div>
            </SwiperSlide>
          </Swiper>
        </section>
        <section
          className="bg-LIGHTBASE bg-opacity-50 pb-4 pt-2"
          style={{
            boxShadow:
              '0 -4px 6px -1px rgba(0, 0, 0, 0.1), 0 -2px 4px -1px rgba(0, 0, 0, 0.06), 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
          }}
        >
          <NavigateBox
            className="m-4 h-20 bg-white"
            path="/mypage/coupon"
            icon={<InboxArrowDownIcon className="size-8 text-PRIMARY" />}
            text="보유 쿠폰"
          />
          <div className="flex gap-2 px-4">
            <div className="flex flex-1 flex-col gap-2">
              <NavigateBox
                className="h-20 bg-white"
                path="/mypage/review"
                icon={<ChatBubbleLeftIcon className="size-6 text-PRIMARY" />}
                text="리뷰 관리"
              />
              <NavigateBox
                className="h-20 bg-white"
                path="/mypage/like"
                icon={
                  <BuildingStorefrontIcon className="size-6 text-PRIMARY" />
                }
                text="찜한 식당"
              />
            </div>
            <NavigateBox
              className="h-42 flex-1 bg-white"
              path="/mypage/transaction"
              icon={<CreditCardIcon className="size-8 text-PRIMARY" />}
              text="결제 내역"
              bigger={true}
            />
          </div>
        </section>
      </main>
      <Modal isOpen={isModalOpen} onClose={closeModal}>
        {modalContent === 'nickname' && (
          <NicknameChange closeModal={closeModal} />
        )}
        {modalContent === 'password' && <PasswordChange />}
      </Modal>
    </Layout>
  );
};

export default MyPage;
