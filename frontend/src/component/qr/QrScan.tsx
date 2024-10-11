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
    </div>
  );
};

export default QrScan;
