interface payment {
  type: string;
  name: string;
  amount: number;
}

type paymentList = {
  transactionId: number;
  amount: number;
  franchise?: {
    id: number;
    name: string;
  };
  createdAt: string;
  details: payment[];
}[];
