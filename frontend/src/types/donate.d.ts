type Donate = {
  franchiseId: number;
  name: string;
  address: string;
  money: number;
  date: string;
}[];

export interface DonateProps {
  onNext: () => void;
  email: string;
  setEmail: React.Dispatch<React.SetStateAction<string>>;
  phoneNumber: string;
  setPhoneNumber: React.Dispatch<React.SetStateAction<string>>;
}

interface Donate1Props {
  onNext: () => void;
  name: string;
  setName: React.Dispatch<React.SetStateAction<string>>;
  phoneNumber: string;
  setPhoneNumber: React.Dispatch<React.SetStateAction<string>>;
  email: string;
  setEmail: React.Dispatch<React.SetStateAction<string>>;
}

interface Donate2Props {
  onNext: () => void;
  count2000: number;
  setCount2000: React.Dispatch<React.SetStateAction<number>>;
  count4000: number;
  setCount4000: React.Dispatch<React.SetStateAction<number>>;
  couponMessage: string;
  setCouponMessage: React.Dispatch<React.SetStateAction<string>>;
  amount: number;
}

interface Donate3Props {
  onNext: () => void;
  accountNumber: string;
  setAccountNumber: React.Dispatch<React.SetStateAction<string>>;
}

interface Donate4Props {
  onNext: () => void;
  amount: number;
  accountNumber: string;
  count2000: number;
  count4000: number;
  couponMessage: string;
}

interface CouponOption {
  value: string;
  label: string;
  id: number;
}
