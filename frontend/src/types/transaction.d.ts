interface payment {
  type: string;
  name: string;
  amount: number;
}

type paymentList = {
  paymentId: number;
  amount: number;
  franchiseId: number;
  franchiseName: string;
  createdAt: string;
  details: payment[];
}[];

interface payInfo {
  franchiseId: number;
  amount: number;
  pin: string;
  cardId?: number | null;
  couponUserId?: number | null;
  couponUnit?: number | null;
}

interface PaymentInfoProps {
  totalPrice: number;
  couponPrice: number;
  onCardSelect?: (cardId: string) => void;
}

interface PaySuccessProps {
  totalPrice: number;
  selectedCoupon: number | null;
  myCoupon: myCoupon[];
}
