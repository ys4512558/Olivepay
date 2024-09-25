type CreditCard = {
  cardId: string;
  realCardNumber: string;
  isDefault: boolean;
  cardCompany: string;
}[];

interface cardSelectProps {
  onCardSelect: (cardId: string) => void;
}
