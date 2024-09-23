interface user {
  id: number;
  name: string;
  phoneNumber: string;
  nickName: string;
}

interface simpleFranchise {
  id: number;
  name: string;
}

interface unwriteReview {
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
