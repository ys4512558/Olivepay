import { Input } from '../common';

const QrInput: React.FC<InputProps> = ({ value, onChange }) => {
  return (
    <Input
      name="qr-price"
      value={value}
      onChange={onChange}
      className="h-16 w-full border-2 text-center text-lg"
    />
  );
};

export default QrInput;
