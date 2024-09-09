type CreditCardProps = {
  cardName: string;
  cardNumber?: string;
  cardOwner?: string;
};

type CouponProps = {
  couponID?: number;
  storeName?: string;
  cost?: number;
  onClick?: () => void;
};

interface PageTitleProps {
  title: string;
}
