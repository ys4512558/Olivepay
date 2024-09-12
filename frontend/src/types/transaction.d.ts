interface payment {
  type: string;
  name: string;
  amount: number;
}

type paymentList = {
  transactionId: number;
  amount: number;
  createdAt: string;
  details: payment[];
}[];
