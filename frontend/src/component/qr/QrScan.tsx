import { useZxing } from 'react-zxing';

const QrScan: React.FC<QrScanProps> = ({ onResult }) => {
  const { ref } = useZxing({
    onDecodeResult(result) {
      onResult(result.getText());
    },
  });

  return (
    <div className="relative mx-8 mt-12 flex flex-col items-center">
      <h3 className="mb-12 text-xl font-semibold">QR 코드를 스캔해주세요</h3>
      <video ref={ref} className="relative h-72 w-full" />
      {/* 네 모서리에 위치한 요소들 */}
      <div className="absolute -left-4 top-[88px] size-12 rounded-tl-md border-l-4 border-t-4 border-PRIMARY"></div>
      <div className="absolute -right-4 top-[88px] size-12 rounded-tr-md border-r-4 border-t-4 border-PRIMARY"></div>
      <div className="absolute -left-4 bottom-2 size-12 rounded-bl-md border-b-4 border-l-4 border-PRIMARY"></div>
      <div className="absolute -right-4 bottom-2 size-12 rounded-br-md border-b-4 border-r-4 border-PRIMARY"></div>
    </div>
  );
};

export default QrScan;
