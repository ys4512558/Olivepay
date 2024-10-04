interface payment {
  type: string;
  name: string;
  amount: number;
}

type paymentList = {
  paymentId: number;
  amount: number;
  franchise?: {
    id: number;
    name: string;
  };
  createdAt: string;
  details: payment[];
}[];

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
