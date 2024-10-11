interface DonateInfo {
  email: string;
  phoneNumber: string;
  coupon2: number;
  coupon4: number;
  money: number;
  message: string;
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
    franchiseName: string;
    address: string;
    money: number;
    date: string;
  }>;
};

interface CouponOption {
  value: number;
  label: string;
}
