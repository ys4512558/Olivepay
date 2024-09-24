export interface DonateProps {
  onNext?: () => void;
}

interface Donate1Props extends DonateProps {
  onNext: () => void;
  name: string;
  setName: React.Dispatch<React.SetStateAction<string>>;
  phoneNumber: string;
  setPhoneNumber: React.Dispatch<React.SetStateAction<string>>;
  email: string;
  setEmail: React.Dispatch<React.SetStateAction<string>>;
}

interface Donate2Props extends DonateProps {
  count2000: number;
  setCount2000: React.Dispatch<React.SetStateAction<number>>;
  count4000: number;
  setCount4000: React.Dispatch<React.SetStateAction<number>>;
  couponMessage: string;
  setCouponMessage: React.Dispatch<React.SetStateAction<string>>;
  amount: number;
}

interface CouponOption {
  value: string;
  label: string;
  id: number;
}

interface Donate3Props extends DonateProps {
  accountNumber: string;
  setAccountNumber: React.Dispatch<React.SetStateAction<string>>;
}

interface Donate4Props extends DonateProps {
  amount: number;
  accountNumber: string;
  count2000: number;
  count4000: number;
  couponMessage: string;
}
