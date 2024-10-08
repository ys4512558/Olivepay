import { useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Layout, Input, Button, BackButton, PageTitle } from '../common';
import { useSnackbar } from 'notistack';
import { ViewfinderCircleIcon } from '@heroicons/react/24/solid';
import { readCardImage } from '../../api/cardApi';
import axios from 'axios';

const CardScanner = () => {
  const navigate = useNavigate();
  const videoRef = useRef<HTMLVideoElement>(null);
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const { enqueueSnackbar } = useSnackbar();
  const [cardNumber, setCardNumber] = useState<string[]>(['', '', '', '']);
  const [expiryDate, setExpiryDate] = useState({ month: '', year: '' });
  const [loading, setLoading] = useState(false);
  const [cameraActive, setCameraActive] = useState(false);
  const [capturedImage, setCapturedImage] = useState<string | null>(null);

  const startCamera = async () => {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      if (videoRef.current) {
        videoRef.current.srcObject = stream;
        setCameraActive(true);
      }
    } catch (error) {
      if (axios.isAxiosError(error)) {
        enqueueSnackbar(
          `${error.response?.data?.data || '카메라를 시작할 수 없습니다.'}`,
          {
            variant: 'error',
          },
        );
      }
    }
  };

  const captureImageAndSendToBackend = async () => {
    if (canvasRef.current && videoRef.current) {
      const context = canvasRef.current.getContext('2d');
      if (context) {
        context.drawImage(
          videoRef.current,
          0,
          0,
          canvasRef.current.width,
          canvasRef.current.height,
        );
        const imageUrl = canvasRef.current.toDataURL();
        setCapturedImage(imageUrl);
        setCameraActive(false);

        canvasRef.current.toBlob(async (blob) => {
          if (blob) {
            const formData = new FormData();
            formData.append('cardImg', blob);

            try {
              setLoading(true);
              const result = await readCardImage(blob);

              if (result.resultCode === 'SUCCESS') {
                setCardNumber(
                  result.data.cardNumber.match(/.{1,4}/g) || ['', '', '', ''],
                );
                setExpiryDate({
                  month: result.data.expirationMonth,
                  year: result.data.expirationYear,
                });
              } else {
                enqueueSnackbar('OCR에 실패했습니다.', {
                  variant: 'error',
                });
              }
            } catch (error) {
              if (axios.isAxiosError(error)) {
                enqueueSnackbar(
                  `OCR API 요청 중 오류가 발생했습니다: ${error.response?.data?.message || error.message}`,
                  { variant: 'error' },
                );
              } else {
                enqueueSnackbar('알 수 없는 오류가 발생했습니다.', {
                  variant: 'error',
                });
              }
            } finally {
              setLoading(false);
            }
          }
        });
      }
    }
  };

  const handleRetake = () => {
    setCapturedImage(null);
    startCamera();
  };

  const handleCompleteScan = () => {
    navigate('/card', {
      state: {
        cardNumbers: cardNumber,
        expiryMM: expiryDate.month,
        expiryYY: expiryDate.year,
      },
    });
  };

  return (
    <Layout>
      <header className="flex w-full items-center justify-between px-10 pt-4">
        <BackButton />
        <div className="flex-grow text-center">
          <PageTitle title="카드 스캔" />
        </div>
        <div className="w-8" />
      </header>

      <main className="flex flex-col gap-y-6 p-5">
        {loading ? (
          <p className="text-center text-gray-500">스캔 중...</p>
        ) : null}
        <p className="p-1 text-center text-base font-bold">
          카드를 정중앙에 맞춘 후 촬영 버튼을 눌러주세요.
        </p>
        <div className="flex flex-col gap-y-4 text-right">
          {!cameraActive && !capturedImage && (
            <div className="flex justify-end">
              <span
                onClick={startCamera}
                className="flex cursor-pointer items-center gap-1 rounded-3xl bg-RED px-3 py-2 text-sm font-bold text-white"
              >
                <ViewfinderCircleIcon className="h-4 w-4" /> 카메라 시작
              </span>
            </div>
          )}

          <div
            title="cameraFrame"
            className="relative rounded-md border-2 border-gray-300 bg-gray-100 pt-[63.1%]"
          >
            {!capturedImage ? (
              <video
                ref={videoRef}
                autoPlay
                className="absolute left-0 top-0 h-full w-full rounded-md object-cover"
              />
            ) : (
              <img
                src={capturedImage}
                alt="Captured"
                className="absolute left-0 top-0 h-full w-full rounded-md object-cover"
              />
            )}
            <canvas
              ref={canvasRef}
              width="640"
              height="430"
              className="hidden"
            />
          </div>

          {cameraActive && (
            <div className="text-center">
              <Button
                label="촬영"
                variant="secondary"
                className="px-6 py-3 text-sm font-bold"
                onClick={captureImageAndSendToBackend}
              />
            </div>
          )}

          {capturedImage && (
            <div className="text-center">
              <Button
                label="재촬영"
                variant="secondary"
                className="px-6 py-3 text-sm font-bold"
                onClick={handleRetake}
              />
            </div>
          )}
        </div>

        <figure className="flex flex-col gap-y-2">
          <p className="ms-3 text-md font-semibold text-gray-600">카드 번호</p>
          <div className="grid grid-cols-4 gap-2">
            {cardNumber.map((num, index) => (
              <Input
                key={index}
                value={num}
                readOnly
                className="border border-gray-300 text-center text-sm"
                placeholder="0000"
              />
            ))}
          </div>
        </figure>

        <figure className="grid grid-cols-2 gap-4">
          <div className="flex flex-col gap-y-2">
            <p className="ms-3 text-md font-semibold text-gray-600">
              유효 기간
            </p>
            <div className="grid grid-cols-2 gap-2">
              <Input
                value={expiryDate.month}
                readOnly
                className="border border-gray-300 p-3 text-center text-sm"
                placeholder="MM"
                maxLength={2}
              />
              <Input
                value={expiryDate.year}
                readOnly
                className="border border-gray-300 p-3 text-center text-sm"
                placeholder="YY"
                maxLength={2}
              />
            </div>
          </div>
        </figure>
        <div className="pb-20 pt-3">
          <Button label="스캔 완료하기" onClick={handleCompleteScan} />
        </div>
      </main>
    </Layout>
  );
};

export default CardScanner;
