import { useState, useCallback } from 'react';
import { useAtom } from 'jotai';
import { couponAtom } from '../atoms/userAtom';
import {
  Layout,
  PageTitle,
  BottomUp,
  Button,
  Modal,
} from '../component/common';
import { QrScan } from '../component/qr';
import {
  CheckPinCode,
  PayDetail,
  PaySuccess,
  PayWaiting,
} from '../component/pay';
import { Helmet } from 'react-helmet';
import { franchise } from '../types/franchise';
import { getFranchiseDetail } from '../api/franchiseApi';
import { getMyCoupon } from '../api/couponApi';
import { pay } from '../api/transactionApi';
import { Client } from '@stomp/stompjs';
import { getPayToken } from '../api/loginApi';
import { enqueueSnackbar } from 'notistack';

const PayPage = () => {
  const [myCoupon, setMyCoupon] = useAtom(couponAtom);
  const [franchiseInfo, setFranchiseInfo] = useState<franchise | null>(null);
  const [steps, setSteps] = useState<number>(1);
  const [qrResult, setQrResult] = useState<string | null>(null);
  const [totalPrice, setTotalPrice] = useState<number>(0);
  const [selectedCoupon, setSelectedCoupon] = useState<number | null>(null);
  const [selectedCouponUnit, setSelectedCouponUnit] = useState<number | null>(
    null,
  );
  const [selectedCardId, setSelectedCardId] = useState<string | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleQrResult = useCallback(
    async (result: string) => {
      const params = new URLSearchParams(result);
      const franchiseId = params.get('franchiseId');
      const amount = params.get('amount');
      if (franchiseId) {
        const store = await getFranchiseDetail(+franchiseId);
        setFranchiseInfo(store);
        const coupons = await getMyCoupon(+franchiseId);
        setMyCoupon(coupons);
      }
      if (amount) {
        setTotalPrice(+amount);
      }

      setQrResult(result);
    },
    [setMyCoupon],
  );

  const handleQrsteps = useCallback(() => {
    setQrResult(null);
    setSteps(2);
  }, []);

  const handlePaySteps = useCallback(() => {
    setSteps(3);
  }, []);

  const handlePaySuccess = useCallback(
    async (pinCode: string) => {
      console.log({
        cardId: selectedCardId ? +selectedCardId : null,
        franchiseId: franchiseInfo ? franchiseInfo.franchiseId : 0,
        amount: totalPrice,
        pin: pinCode,
        couponUserId: selectedCoupon,
        couponUnit: selectedCouponUnit,
      });
      try {
        // 1. 결제 요청
        const result = await pay({
          cardId: selectedCardId ? +selectedCardId : null,
          franchiseId: franchiseInfo ? franchiseInfo.franchiseId : 0,
          amount: totalPrice,
          pin: pinCode,
          couponUserId: selectedCoupon,
          couponUnit: selectedCouponUnit,
        });

        setIsModalOpen(true);

        const tempKey = result.data.key;

        // 2. 토큰 발급 요청
        const token = await getPayToken();

        // 3. WebSocket 연결 설정
        const stompClient = new Client({
          brokerURL: `${import.meta.env.VITE_WEBSOCKET_URL}?paymentToken=${token}`,
          connectHeaders: {},
          debug: (str) => {
            console.log(str);
          },
        });

        // WebSocket 연결 성공 시 실행
        stompClient.onConnect = () => {
          console.log('Connected to WebSocket');
          // WebSocket 구독 설정, 서버로부터 메시지를 받음
          stompClient.subscribe(
            `/payments/payment-state-check/${tempKey}`,
            (msg) => {
              console.log(msg.body);
              try {
                const parsedMessage = JSON.parse(msg.body);
                if (parsedMessage) {
                  console.log(parsedMessage);
                  setIsModalOpen(false);
                  if (parsedMessage.resultCode === 'SUCCESS') {
                    setSteps(4);
                  } else {
                    enqueueSnackbar(parsedMessage.message, {
                      variant: 'error',
                    });
                  }
                  stompClient.deactivate();
                }
              } catch (error) {
                console.log('메시지 파싱 도중 오류 발생:', error);
              }
            },
          );
        };

        // STOMP 프로토콜 에러 처리
        stompClient.onStompError = (frame) => {
          console.error('STOMP 에러 발생', frame);
        };

        // WebSocket 자체 에러 처리
        stompClient.onWebSocketError = (event) => {
          console.error('WebSocket 에러 발생', event);
        };

        // WebSocket 연결 시작
        stompClient.activate();
      } catch (error) {
        if (error instanceof Error) {
          enqueueSnackbar('비밀번호가 틀렸습니다.', { variant: 'error' });
        }
      }
    },
    [
      selectedCardId,
      franchiseInfo,
      totalPrice,
      selectedCoupon,
      selectedCouponUnit,
    ],
  );

  const handleCouponSelect = useCallback(
    (couponIndex: number | null, couponUnit: number | null) => {
      setSelectedCoupon(couponIndex);
      setSelectedCouponUnit(couponUnit);
    },
    [],
  );

  const handleCardSelect = useCallback((cardId: string | null) => {
    setSelectedCardId(cardId);
  }, []);

  const closeModal = useCallback(() => {
    setIsModalOpen(false);
  }, []);

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="QR을 통해 음식점과 금액을 인식하고 결제를 진행할 수 있습니다."
        />
      </Helmet>
      <Layout>
        <header className="mt-4 px-8 text-center">
          <PageTitle title="결제 " />
        </header>
        <main className="mb-16">
          {steps === 1 && <QrScan onResult={handleQrResult} />}
          {steps === 2 && (
            <PayDetail
              handlePaySteps={handlePaySteps}
              totalPrice={totalPrice}
              selectedCoupon={selectedCoupon}
              onCardSelect={handleCardSelect}
              onCouponSelect={handleCouponSelect}
              myCoupon={myCoupon}
            />
          )}
          {steps === 3 && (
            <CheckPinCode
              handlePaySuccess={(pinCode: string) => handlePaySuccess(pinCode)}
            />
          )}
          {steps === 4 && (
            <PaySuccess
              totalPrice={totalPrice}
              myCoupon={myCoupon}
              selectedCoupon={selectedCoupon}
            />
          )}
        </main>
      </Layout>
      {qrResult && (
        <BottomUp
          children={
            <div className="mb-24">
              <div className="mb-4 ml-4">
                <h2 className="font-semibold">
                  {franchiseInfo?.franchiseName}
                </h2>
                <div className="mt-2 text-base text-DARKBASE">
                  {franchiseInfo?.address}
                </div>
              </div>
              <Button label="입장하기" onClick={handleQrsteps} />
            </div>
          }
        />
      )}
      <Modal
        isOpen={isModalOpen}
        onClose={closeModal}
        forPay={true}
        children={<PayWaiting />}
      />
    </>
  );
};

export default PayPage;
