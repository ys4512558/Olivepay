import { Input } from '../common';

const QrInput: React.FC<InputProps> = ({ value, onChange }) => {
  return (
    <Input
      name="qr-price"
      value={value}
      onChange={onChange}
      className="h-24 w-full"
    />
  );
};

export default QrInput;
