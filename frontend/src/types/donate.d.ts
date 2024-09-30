interface DonateInfo {
  email: string;
  phoneNumber: string;
  count2000: number;
  count4000: number;
  money: number;
  couponMessage: string;
  accountNumber: string;
}

interface CommonProps {
  onNext: () => void;
  donateInfo: DonateInfo;
  setDonateInfo: React.Dispatch<React.SetStateAction<DonateInfo>>;
}

export interface DonateProps {
  onNext: () => void;
  email: string;
  setEmail: React.Dispatch<React.SetStateAction<string>>;
  phoneNumber: string;
  setPhoneNumber: React.Dispatch<React.SetStateAction<string>>;
}

type Donate = {
  donationList: Array<{
    franchiseId: number;
    name: string;
    address: string;
    money: number;
    date: string;
  }>;
};

interface CouponOption {
  value: number;
  label: string;
}
