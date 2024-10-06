interface user {
  memberId: number;
  name: string;
  phoneNumber: string;
  nickname: string;
  role: 'USER' | 'TEMP_USER';
}

interface simpleFranchise {
  id: number;
  name: string;
}

interface unwriteReview {
  reviewId: number;
  paymentId: number;
  franchise: simpleFranchise;
  createdAt: string;
}

interface review {
  reviewId: number;
  franchise?: simpleFranchise;
  memberId?: number;
  memberName?: string;
  stars: number;
  content: string;
}

interface infoChangeProps {
  closeModal: () => void;
}

interface PasswordCheckProps {
  label: string;
  value: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onClick: () => void;
}

interface UserProps {
  user?: user;
  className?: string;
}
